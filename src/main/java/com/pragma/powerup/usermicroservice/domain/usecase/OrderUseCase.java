package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtProvider;
import com.pragma.powerup.usermicroservice.domain.api.IOrderServicePort;

import com.pragma.powerup.usermicroservice.domain.clientapi.IMessageClientPort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IOrderLogClientPort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IUserClientPort;

import com.pragma.powerup.usermicroservice.domain.exceptions.EmployeeNotAssignedException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderInProgressException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotFoundException;

import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.*;
import com.pragma.powerup.usermicroservice.domain.orderMessage.OrderLogJsonSerialize;
import com.pragma.powerup.usermicroservice.domain.spi.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.pragma.powerup.usermicroservice.domain.orderMessage.OrderMessage.*;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IUserClientPort userClientPort;
    private final IMessageClientPort messageClientPort;
    private final IOrderMessagePersistencePort orderMessagePersistencePort;
    private final IOrderLogClientPort orderLogClientPort;

    public OrderUseCase(IOrderDishPersistencePort orderDishPersistencePort, IOrderPersistencePort orderPersistencePort, IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort, IDishPersistencePort dishPersistencePort, IUserClientPort userClientPort, IMessageClientPort messageClientPort, IOrderMessagePersistencePort orderMessagePersistencePort, IOrderLogClientPort orderLogClientPort) {
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.userClientPort = userClientPort;
        this.messageClientPort = messageClientPort;
        this.orderMessagePersistencePort = orderMessagePersistencePort;
        this.orderLogClientPort = orderLogClientPort;
    }

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public void createOrder(Order order, HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        String status = "Awaiting";
        String token = request.getHeader("Authorization");
        Long clientId = jwtProvider.getUserIdFromToken(token);

        if (hasInProgressOrder(clientId)) {
            throw new OrderInProgressException();
        }

        order.setClientId(clientId);
        order.setDateTime(dateTime);
        order.setStatus(status);
        double amount = calculateTotalAmount(order.getOrderDishes());
        order.setAmount(amount);
        Order orderEntity = orderPersistencePort.saveOrder(order);

        order.getOrderDishes().forEach(orderDish -> orderDish.setOrder(orderEntity));
        orderDishPersistencePort.saveOrderDish(order.getOrderDishes());

        OrderLogJson orderLog = new OrderLogJson();
        orderLog.setOrderId(orderEntity.getId());
        orderLog.setPreviousStatus("");
        orderLog.setNewStatus(orderEntity.getStatus());
        orderLog.setTimestamp(LocalDateTime.now());

        orderLogClientPort.saveOrderLog(OrderLogJsonSerialize.serializeToJson(orderLog));
    }

    private double calculateTotalAmount(List<OrderDish> orderDishes) {
        double totalAmount = 0.0;
        for (OrderDish orderDish : orderDishes) {
            Dish dish = orderDish.getDish();
            Dish persistedDish = dishPersistencePort.getDishById(dish.getId());
            if (persistedDish != null) {
                totalAmount += persistedDish.getPrice() * orderDish.getQuantity();
            }
        }
        return totalAmount;
    }

    private boolean hasInProgressOrder(Long clientId) {
        List<Order> clientOrders = orderPersistencePort.getOrdersByClientId(clientId);

        for (Order order : clientOrders) {
            if (order.getStatus().equalsIgnoreCase("In process") || order.getStatus().equalsIgnoreCase("Awaiting")) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Order> getRestaurantOrder(int pageNumber, int pageSize, String status, Long employeeId) {
        validateRange(pageNumber, pageSize);
        pageNumber -= 1;
        Long idRestaurant = employeeRestaurantPersistencePort.getRestaurantEmployee(employeeId).getRestaurant().getId();
        List<OrderDish> orderDishes = orderDishPersistencePort.getRestaurantOrderDish(status, idRestaurant);
        List<Order> orders = orderPersistencePort.getRestaurantOrder(pageNumber, pageSize, status, idRestaurant);

        for (Order order : orders) {
            for (OrderDish orderDish : orderDishes) {
                if (order.getId() != null && order.getId().equals(orderDish.getOrder().getId())) {
                    if (order.getOrderDishes() == null) {
                        order.setOrderDishes(new ArrayList<>());
                    }
                    order.getOrderDishes().add(orderDish);
                }
            }
        }

        return orders;
    }

    @Override
    public void assignEmployeeToOrders(List<Long> orderIds, Long employeeId) {
        List<Order> orders = new ArrayList<>();

        for (Long orderId : orderIds) {
            Order order = orderPersistencePort.getOrderById(orderId);

            if (order == null) {
                throw new OrderNotFoundException();
            }

            Long restaurantId = order.getIdRestaurant().getId();
            boolean isEmployeeAssigned = employeeRestaurantPersistencePort.isEmployeeAssignedToRestaurant(employeeId, restaurantId);

            if (isEmployeeAssigned) {
                if (order.getAssignedEmployeeId() != null) {
                    throw new EmployeeAssignedToTheSameRestaurantException();
                }

                if (order.getStatus().equalsIgnoreCase("awaiting")) {
                    order.setAssignedEmployeeId(employeeId);
                    order.setStatus("In process");
                    orders.add(order);
                } else {
                    throw new InvalidOrderStatusException();
                }
            } else {
                throw new EmployeeNotAssignedException();
            }
            OrderLogJson orderLog = new OrderLogJson();
            orderLog.setOrderId(order.getId());
            orderLog.setPreviousStatus("Awaiting");
            orderLog.setNewStatus(order.getStatus());
            orderLog.setTimestamp(LocalDateTime.now());

            orderLogClientPort.saveOrderLog(OrderLogJsonSerialize.serializeToJson(orderLog));
        }

        orderPersistencePort.saveOrderAll(orders);
    }


    private void validateRange(int pageNumber, int pageSize) {
        if (pageNumber <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void updateStatusToReady(Long id, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        Order order = orderPersistencePort.getOrderById(id);
        validateOrder(order, id);

        if (!order.getStatus().equalsIgnoreCase("In process")) {
            throw new InvalidOrderStatusException();
        }

        order.setStatus("Ready");

        Long clientId = order.getClientId();
        UserResponseDto userResponseDto = userClientPort.getUserById(clientId, header);
        String phone = userResponseDto.getPhone();
        exists(phone);

        String pin = codeMessage(order, phone);
        messageClientPort.sendPinMessage(createJson(order, phone, pin));

        orderMessagePersistencePort.savePin(new Pin(order, pin));
        orderPersistencePort.saveOrder(order);

        OrderLogJson orderLog = new OrderLogJson();
        orderLog.setOrderId(order.getId());
        orderLog.setPreviousStatus("In process");
        orderLog.setNewStatus(order.getStatus());
        orderLog.setTimestamp(LocalDateTime.now());

        orderLogClientPort.saveOrderLog(OrderLogJsonSerialize.serializeToJson(orderLog));
    }
    @Override
    public void updateStatusToDelivered(Long orderId, String securityPin) {
        Order order = orderPersistencePort.getOrderById(orderId);

        if (order == null) {
            throw new OrderNotFoundException();
        }

        if (!order.getStatus().equalsIgnoreCase("Ready")) {
            throw new OrderMustBeInReadyStatus();
        }

        Pin pin = orderMessagePersistencePort.getPinByOrderId(orderId);

        if (pin == null || !pin.getPin().equals(securityPin)) {
            throw new InvalidPinException();
        }

        order.setStatus("Delivered");
        orderPersistencePort.saveOrder(order);
    }

    @Override
    public void cancelOrder(Long orderId, Long clientId) {
        Order order = orderPersistencePort.getOrderById(orderId);

        if (order == null) {
            throw new OrderNotFoundException();
        }

        if (!order.getClientId().equals(clientId)) {
            throw new UnauthorizedOrderCancellationException();
        }

        if (!order.getStatus().equalsIgnoreCase("Awaiting")) {
            throw new InvalidOrderStatusException();
        }

        order.setStatus("Cancelled");
        orderPersistencePort.saveOrder(order);
}
    @Override
    public void saveOrderLog(OrderLogJson orderLogJson) {
        String json = OrderLogJsonSerialize.serializeToJson(orderLogJson);
        orderLogClientPort.saveOrderLog(json);
    }

    @Override
    public List<OrderLogJson> getOrderLogsByOrderId(Long orderId, Long clientId) {
        Order order = orderPersistencePort.getOrderById(orderId);

        if (order == null) {
            throw new OrderNotFoundException();
        }

        if (!order.getClientId().equals(clientId)) {
            throw new UnauthorizedOrderAccessException();
        }

        return orderLogClientPort.getOrderLogsByOrderId(orderId);
    }
    @Override
    public String calculateElapsedTime(Long orderId, Long ownerId) {
        Order order = orderPersistencePort.getOrderById(orderId);

        if (order == null) {
            throw new OrderNotFoundException();
        }

        Long restaurantOwnerId = order.getIdRestaurant().getIdOwner();

        if (!restaurantOwnerId.equals(ownerId)) {
            throw new UnauthorizedOrderAccessException();
        }
        return orderLogClientPort.calculateElapsedTimeByOrderId(orderId);
    }

    public String calculateAverageElapsedTimeByEmployee(Long assignedEmployeeId, Long ownerId) {

        List<Order> employeeOrders = orderPersistencePort.getOrdersByEmployeeId(assignedEmployeeId);
        List<Duration> orderDurations = new ArrayList<>();

        for (Order order : employeeOrders) {
            if (order.getStatus().equalsIgnoreCase("Delivered")) {
                Long orderId = order.getId();
                String elapsedTimeStr = orderLogClientPort.calculateElapsedTimeByOrderId(orderId);

                if (elapsedTimeStr != null) {
                    Duration elapsedTime = Duration.parse(elapsedTimeStr);
                    orderDurations.add(elapsedTime);
                }
            }
        }

        if (orderDurations.isEmpty()) {
            return "No completed orders found for the employee";
        }

        Duration totalElapsedTime = Duration.ZERO;
        for (Duration duration : orderDurations) {
            totalElapsedTime = totalElapsedTime.plus(duration);
        }

        Duration averageElapsedTime = totalElapsedTime.dividedBy(orderDurations.size());
        return averageElapsedTime.toString();
    }


}
