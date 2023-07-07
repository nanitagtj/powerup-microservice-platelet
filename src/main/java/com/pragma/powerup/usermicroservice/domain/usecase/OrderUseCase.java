package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.domain.api.IOrderServicePort;

import com.pragma.powerup.usermicroservice.domain.clientapi.IMessageClientPort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IOrderLogClientPort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IUserClientPort;

import com.pragma.powerup.usermicroservice.domain.comparator.DishComparator;
import com.pragma.powerup.usermicroservice.domain.enums.DishTypeEnum;
import com.pragma.powerup.usermicroservice.domain.exceptions.EmployeeNotAssignedException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderInProgressException;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNotFoundException;

import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.*;
import com.pragma.powerup.usermicroservice.domain.orderMessage.OrderLogJsonSerialize;
import com.pragma.powerup.usermicroservice.domain.spi.*;
import com.pragma.powerup.usermicroservice.domain.validations.OrderUseCaseValidations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static com.pragma.powerup.usermicroservice.domain.orderMessage.OrderMessage.*;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final IUserClientPort userClientPort;
    private final IMessageClientPort messageClientPort;
    private final IOrderMessagePersistencePort orderMessagePersistencePort;
    private final IOrderLogClientPort orderLogClientPort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final OrderUseCaseValidations validations;
    private PriorityQueue<OrderDish> orderDishQueue;

    public OrderUseCase(IOrderDishPersistencePort orderDishPersistencePort, IOrderPersistencePort orderPersistencePort, IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort, IUserClientPort userClientPort, IMessageClientPort messageClientPort, IOrderMessagePersistencePort orderMessagePersistencePort, IOrderLogClientPort orderLogClientPort, IRestaurantPersistencePort restaurantPersistencePort, OrderUseCaseValidations validations) {
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.userClientPort = userClientPort;
        this.messageClientPort = messageClientPort;
        this.orderMessagePersistencePort = orderMessagePersistencePort;
        this.orderLogClientPort = orderLogClientPort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.validations = validations;
        orderDishQueue = new PriorityQueue<>(new DishComparator());
    }

    @Override
    public void createOrder(Order order, Long clientId) {
        LocalDateTime dateTime = LocalDateTime.now();
        String status = "Awaiting";

        if (validations.hasInProgressOrder(clientId)) {
            throw new OrderInProgressException();
        }

        order.setClientId(clientId);
        order.setDateTime(dateTime);
        order.setStatus(status);



        double amount = validations.calculateTotalAmount(order.getOrderDishes());
        order.setAmount(amount);
        Order orderEntity = orderPersistencePort.saveOrder(order);

        order.getOrderDishes().forEach(orderDish -> orderDish.setOrder(orderEntity));
        orderDishPersistencePort.saveOrderDish(order.getOrderDishes());

        validations.saveOrderLogForCreateOrder(orderEntity.getId());
    }

    @Override
    public List<OrderDish> addOrder(Long orderId) {
        Order order = orderPersistencePort.getOrderById(orderId);
        if (order == null) {
            return Collections.emptyList();
        }

        DishTypeEnum dishType = orderDishPersistencePort.getDishTypeByOrderId(orderId);
        if (dishType != null) {
            orderDishQueue.add(new OrderDish(order.getId(), dishType));
        }

        PriorityQueue<OrderDish> tempQueue = new PriorityQueue<>(orderDishQueue);

        List<OrderDish> orderedOrderDishes = new ArrayList<>();
        while (!tempQueue.isEmpty()) {
            orderedOrderDishes.add(tempQueue.poll());
        }

        return orderedOrderDishes;
    }

    @Override
    public List<DishTypeEnum> takeOrder() {
        if (orderDishQueue.isEmpty()) {
            throw new OrderNotFoundException();
        }

        OrderDish takenOrderDish = orderDishQueue.poll();

        List<DishTypeEnum> remainingDishTypes = new ArrayList<>();
        for (OrderDish orderDish : orderDishQueue) {
            remainingDishTypes.add(orderDish.getDishTypeEnum());
        }

        return remainingDishTypes;
    }

    @Override
    public List<OrderDish> pendingOrders() {
        if (orderDishQueue.isEmpty()) {
            throw new OrderNotFoundException();
        }

        List<OrderDish> pendingOrderDishes = new ArrayList<>();
        for (OrderDish orderDish : orderDishQueue) {
            Long orderId = orderDish.getOrder().getId();
            DishTypeEnum dishTypeEnum = orderDish.getDishTypeEnum();

            OrderDish completeOrderDish = orderDishPersistencePort.getOrderDishByOrderIdAndDishType(orderId, dishTypeEnum);
            pendingOrderDishes.add(completeOrderDish);
        }

        return pendingOrderDishes;
    }

    @Override
    public List<Order> getRestaurantOrder(int pageNumber, int pageSize, String status, Long employeeId) {
        validations.validateRange(pageNumber, pageSize);
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
                validations.checkAndAssignOrderToEmployee(order, employeeId, orders);
            } else {
                throw new EmployeeNotAssignedException();
            }
        }

        orderPersistencePort.saveOrderAll(orders);
    }

    @Override
    public void updateStatusToReady(Long id, String authorizationHeader) {
        Order order = orderPersistencePort.getOrderById(id);
        validateOrder(order, id);

        if (!order.getStatus().equalsIgnoreCase("In process")) {
            throw new InvalidOrderStatusException();
        }

        order.setStatus("Ready");

        Long clientId = order.getClientId();
        UserResponseDto userResponseDto = userClientPort.getUserById(clientId, authorizationHeader);
        String phone = userResponseDto.getPhone();
        exists(phone);

        String pin = codeMessage(order, phone);
        messageClientPort.sendPinMessage(createJson(order, phone, pin));

        orderMessagePersistencePort.savePin(new Pin(order, pin));
        orderPersistencePort.saveOrder(order);

        validations.saveOrderLogForUpdateStatusToReady(order.getId());
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
            throw new NoCompletedOrdersException();
        }

        Duration totalElapsedTime = Duration.ZERO;
        for (Duration duration : orderDurations) {
            totalElapsedTime = totalElapsedTime.plus(duration);
        }

        Duration averageElapsedTime = totalElapsedTime.dividedBy(orderDurations.size());
        return averageElapsedTime.toString();
    }

    @Override
    public List<EmployeeRanking> displayEmployeeRanking(Long ownerId) {
        List<EmployeeRanking> ranking = new ArrayList<>();

        Long restaurantId = restaurantPersistencePort.getRestaurantByOwnerId(ownerId).getId();
        List<EmployeeRestaurant> employees = employeeRestaurantPersistencePort.getEmployeeRestaurantsByRestaurantId(restaurantId);
        for (EmployeeRestaurant employee : employees) {
            Long employeeId = employee.getEmployeeId();
            String averageElapsedTimeStr = calculateAverageElapsedTimeByEmployee(employeeId, ownerId);
            if (!averageElapsedTimeStr.equals("No completed orders found for the employee")) {
                EmployeeRanking employeeRanking = new EmployeeRanking();
                employeeRanking.setEmployeeId(employeeId);
                employeeRanking.setAverageElapsedTime(averageElapsedTimeStr);
                ranking.add(employeeRanking);
            }
        }

        ranking.sort(Comparator.comparing(e -> Duration.parse(e.getAverageElapsedTime())));
        return ranking;
    }

    @Override
    public List<OrderDish> getOrderDishes() {
        return new ArrayList<>(orderDishQueue);
    }

}
