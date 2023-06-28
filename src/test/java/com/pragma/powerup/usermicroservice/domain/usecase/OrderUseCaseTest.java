package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.domain.clientapi.IMessageClientPort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IOrderLogClientPort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IUserClientPort;
import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.*;
import com.pragma.powerup.usermicroservice.domain.spi.*;
import com.pragma.powerup.usermicroservice.domain.validations.OrderUseCaseValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {
    @Mock
    private IOrderDishPersistencePort orderDishPersistencePort;
    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    @Mock
    private IDishPersistencePort dishPersistencePort;
    @Mock
    private IUserClientPort userClientPort;
    @Mock
    private IMessageClientPort messageClientPort;
    @Mock
    private IOrderMessagePersistencePort orderMessagePersistencePort;
    @Mock
    private IOrderLogClientPort orderLogClientPort;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private OrderUseCaseValidations validations;

    @Captor
    private ArgumentCaptor<Order> orderCaptor;
    @Captor
    private ArgumentCaptor<List<OrderDish>> orderDishesCaptor;
    @Captor
    private ArgumentCaptor<String> orderLogCaptor;
    @Captor
    private ArgumentCaptor<List<Order>> ordersCaptor;
    private OrderUseCase orderUseCase;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(orderDishPersistencePort, orderPersistencePort,
                employeeRestaurantPersistencePort, userClientPort,
                messageClientPort, orderMessagePersistencePort, orderLogClientPort,
                restaurantPersistencePort, validations);

    }

    @Test
    public void createOrder_ValidOrder_SuccessfullyCreated() {
        // Arrange
        Long clientId = 123L;
        Order order = new Order();
        String status = "Awaiting";
        order.setOrderDishes(new ArrayList<>());

        when(validations.hasInProgressOrder(clientId)).thenReturn(false);
        when(orderPersistencePort.saveOrder(any(Order.class))).thenReturn(order);
        doNothing().when(orderLogClientPort).saveOrderLog(any(String.class));
        // Act
        orderUseCase.createOrder(order, clientId);
        // Assert
        verify(orderPersistencePort).saveOrder(orderCaptor.capture());
        verify(orderDishPersistencePort).saveOrderDish(orderDishesCaptor.capture());

        Order capturedOrder = orderCaptor.getValue();

        assertEquals(clientId, capturedOrder.getClientId());
        assertEquals(status, capturedOrder.getStatus());

        verifyNoMoreInteractions(orderPersistencePort, orderDishPersistencePort, orderLogClientPort);
    }


    @Test
    public void createOrder_InProgressOrder_ThrowsOrderInProgressException() {
        // Arrange
        Long clientId = 123L;
        Order order = new Order();

        when(validations.hasInProgressOrder(clientId)).thenReturn(true);

        // Act & Assert
        assertThrows(OrderInProgressException.class, () -> {
            orderUseCase.createOrder(order, clientId);
        });

        verifyNoMoreInteractions(orderPersistencePort, orderDishPersistencePort, orderLogClientPort);
    }

    @Test
    public void getRestaurantOrder_ValidData_ReturnsOrders() {
        // Arrange
        int pageNumber = 1;
        int pageSize = 10;
        String status = "Awaiting";
        Long employeeId = 123L;

        EmployeeRestaurantEntity employeeRestaurantEntity = new EmployeeRestaurantEntity();
        employeeRestaurantEntity.setRestaurant(new RestaurantEntity());
        when(employeeRestaurantPersistencePort.getRestaurantEmployee(employeeId)).thenReturn(employeeRestaurantEntity);

        List<OrderDish> orderDishes = Arrays.asList(new OrderDish(), new OrderDish());
        when(orderDishPersistencePort.getRestaurantOrderDish(status, employeeRestaurantEntity.getRestaurant().getId())).thenReturn(orderDishes);

        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderPersistencePort.getRestaurantOrder(pageNumber - 1, pageSize, status, employeeRestaurantEntity.getRestaurant().getId())).thenReturn(orders);

        // Act
        List<Order> result = orderUseCase.getRestaurantOrder(pageNumber, pageSize, status, employeeId);

        // Assert
        assertEquals(orders, result);

        verify(validations).validateRange(pageNumber, pageSize);
        verify(employeeRestaurantPersistencePort).getRestaurantEmployee(employeeId);
        verify(orderDishPersistencePort).getRestaurantOrderDish(status, employeeRestaurantEntity.getRestaurant().getId());
        verify(orderPersistencePort).getRestaurantOrder(pageNumber - 1, pageSize, status, employeeRestaurantEntity.getRestaurant().getId());
    }

    @Test
    void testAssignEmployeeToOrders_Successfully() {
        // Arrange
        long orderId = 1;
        long employeeId = 1;
        List<Long> orderIds = new ArrayList<>();
        orderIds.add(orderId);

        Order order = new Order();
        order.setId(orderId);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        order.setIdRestaurant(restaurant);

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(employeeRestaurantPersistencePort.isEmployeeAssignedToRestaurant(employeeId, order.getIdRestaurant().getId())).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> orderUseCase.assignEmployeeToOrders(orderIds, employeeId));

        // Assert
        verify(orderPersistencePort, times(1)).saveOrderAll(anyList());
    }

    @Test
    void testAssignEmployeeToOrders_EmployeeNotAssigned() {
        // Arrange
        long orderId = 1;
        long employeeId = 1;
        List<Long> orderIds = new ArrayList<>();
        orderIds.add(orderId);

        Order order = new Order();
        order.setId(orderId);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(2L);
        order.setIdRestaurant(restaurant);

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(employeeRestaurantPersistencePort.isEmployeeAssignedToRestaurant(employeeId, order.getIdRestaurant().getId())).thenReturn(false);

        // Act & Assert
        assertThrows(EmployeeNotAssignedException.class, () -> orderUseCase.assignEmployeeToOrders(orderIds, employeeId));
        verify(orderPersistencePort, never()).saveOrderAll(anyList());
    }
    @Test
    void updateStatusToReady_ValidOrder_SuccessfullyUpdatesStatus() {
        // Arrange
        Long orderId = 1L;
        String authorizationHeader = "Bearer token";

        Order order = new Order();
        order.setId(orderId);
        order.setStatus("In process");
        order.setClientId(123L);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        order.setIdRestaurant(restaurant);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setPhone("123456789");

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(userClientPort.getUserById(eq(123L), eq(authorizationHeader))).thenReturn(userResponseDto);

        // Act
        orderUseCase.updateStatusToReady(orderId, authorizationHeader);

        // Assert
        assertEquals("Ready", order.getStatus());
        verify(messageClientPort).sendPinMessage(any());
        verify(orderMessagePersistencePort).savePin(any(Pin.class));
        verify(orderPersistencePort).saveOrder(order);
        verify(validations).saveOrderLogForUpdateStatusToReady(orderId);
    }

    @Test
    void updateStatusToReady_InvalidOrder_ThrowsOrderNotFoundException() {
        // Arrange
        Long orderId = 1L;
        String authorizationHeader = "Bearer token";

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(null);

        // Act & Assert
        assertThrows(OrderNoFoundException.class, () -> {
            orderUseCase.updateStatusToReady(orderId, authorizationHeader);
        });
    }

    @Test
    void updateStatusToReady_InvalidOrderStatus_ThrowsInvalidOrderStatusException() {
        // Arrange
        Long orderId = 1L;
        String authorizationHeader = "Bearer token";

        Order order = new Order();
        order.setId(orderId);
        order.setStatus("InvalidStatus");

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);

        // Act & Assert
        assertThrows(InvalidOrderStatusException.class, () -> {
            orderUseCase.updateStatusToReady(orderId, authorizationHeader);
        });
    }

    @Test
    void updateStatusToDelivered_ValidOrderAndPin_StatusUpdatedToDelivered() {
        // Arrange
        Long orderId = 1L;
        String securityPin = "1234";
        Order order = new Order();
        order.setId(orderId);
        order.setStatus("Ready");

        Pin pin = new Pin(order, securityPin);

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(orderMessagePersistencePort.getPinByOrderId(orderId)).thenReturn(pin);

        // Act
        orderUseCase.updateStatusToDelivered(orderId, securityPin);

        // Assert
        assertEquals("Delivered", order.getStatus());
        verify(orderPersistencePort, times(1)).saveOrder(order);
    }

    @Test
    void updateStatusToDelivered_InvalidOrder_OrderNotFoundExceptionThrown() {
        // Arrange
        Long orderId = 1L;

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(null);

        // Act & Assert
        assertThrows(OrderNotFoundException.class, () -> {
            orderUseCase.updateStatusToDelivered(orderId, "1234");
        });
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

    @Test
    void updateStatusToDelivered_OrderNotInReadyStatus_OrderMustBeInReadyStatusThrown() {
        // Arrange
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus("NotReady");

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);

        // Act & Assert
        assertThrows(OrderMustBeInReadyStatus.class, () -> {
            orderUseCase.updateStatusToDelivered(orderId, "1234");
        });
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

    @Test
    void updateStatusToDelivered_InvalidPin_InvalidPinExceptionThrown() {
        // Arrange
        Long orderId = 1L;
        String securityPin = "1234";
        Order order = new Order();
        order.setId(orderId);
        order.setStatus("Ready");

        Pin pin = new Pin(order, "5678");

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(orderMessagePersistencePort.getPinByOrderId(orderId)).thenReturn(pin);

        // Act & Assert
        assertThrows(InvalidPinException.class, () -> {
            orderUseCase.updateStatusToDelivered(orderId, securityPin);
        });
        verify(orderPersistencePort, never()).saveOrder(any(Order.class));
    }

    @Test
    void cancelOrder_WithValidOrderAndClient_ShouldCancelOrder() {
        // Arrange
        Long orderId = 1L;
        Long clientId = 100L;
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(clientId);
        order.setStatus("Awaiting");

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);

        // Act
        orderUseCase.cancelOrder(orderId, clientId);

        // Assert
        assertEquals("Cancelled", order.getStatus());
        verify(orderPersistencePort, times(1)).saveOrder(order);
    }

    @Test
    void cancelOrder_WithInvalidOrder_ShouldThrowOrderNotFoundException() {
        // Arrange
        Long orderId = 1L;
        Long clientId = 100L;

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(null);

        // Act & Assert
        assertThrows(OrderNotFoundException.class, () -> orderUseCase.cancelOrder(orderId, clientId));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void cancelOrder_WithUnauthorizedClient_ShouldThrowUnauthorizedOrderCancellationException() {
        // Arrange
        Long orderId = 1L;
        Long clientId = 100L;
        Long unauthorizedClientId = 200L;
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(clientId);
        order.setStatus("Awaiting");

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);

        // Act & Assert
        assertThrows(UnauthorizedOrderCancellationException.class, () -> orderUseCase.cancelOrder(orderId, unauthorizedClientId));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void cancelOrder_WithInvalidOrderStatus_ShouldThrowInvalidOrderStatusException() {
        // Arrange
        Long orderId = 1L;
        Long clientId = 100L;
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(clientId);
        order.setStatus("In progress");

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);

        // Act & Assert
        assertThrows(InvalidOrderStatusException.class, () -> orderUseCase.cancelOrder(orderId, clientId));
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    public void getOrderLogsByOrderId_WithValidOrderAndClientId_ReturnsOrderLogs() {
        // Arrange
        Long orderId = 1L;
        Long clientId = 123L;
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(clientId);

        List<OrderLogJson> expectedLogs = new ArrayList<>();
        expectedLogs.add(new OrderLogJson());
        expectedLogs.add(new OrderLogJson());

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(orderLogClientPort.getOrderLogsByOrderId(orderId)).thenReturn(expectedLogs);

        // Act
        List<OrderLogJson> actualLogs = orderUseCase.getOrderLogsByOrderId(orderId, clientId);

        // Assert
        assertEquals(expectedLogs, actualLogs);
        verify(orderPersistencePort).getOrderById(orderId);
        verify(orderLogClientPort).getOrderLogsByOrderId(orderId);
    }

    @Test
    public void getOrderLogsByOrderId_WithInvalidOrder_ThrowsOrderNotFoundException() {
        // Arrange
        Long orderId = 1L;
        Long clientId = 123L;

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(null);

        // Act & Assert
        assertThrows(OrderNotFoundException.class, () -> {
            orderUseCase.getOrderLogsByOrderId(orderId, clientId);
        });

        verify(orderPersistencePort).getOrderById(orderId);
        verifyNoInteractions(orderLogClientPort);
    }

    @Test
    public void getOrderLogsByOrderId_WithUnauthorizedAccess_ThrowsUnauthorizedOrderAccessException() {
        // Arrange
        Long orderId = 1L;
        Long clientId = 123L;
        Order order = new Order();
        order.setId(orderId);
        order.setClientId(456L); // Different client ID

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);

        // Act & Assert
        assertThrows(UnauthorizedOrderAccessException.class, () -> {
            orderUseCase.getOrderLogsByOrderId(orderId, clientId);
        });

        verify(orderPersistencePort).getOrderById(orderId);
        verifyNoInteractions(orderLogClientPort);
    }

    @Test
    public void testCalculateElapsedTime_ShouldReturnElapsedTime() {
        // Arrange
        Long orderId = 1L;
        Long ownerId = 2L;
        Long restaurantOwnerId = 2L;
        String expectedElapsedTime = "2 hours";

        Order order = new Order();
        order.setIdRestaurant(new Restaurant());
        order.getIdRestaurant().setIdOwner(restaurantOwnerId);

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);
        when(orderLogClientPort.calculateElapsedTimeByOrderId(orderId)).thenReturn(expectedElapsedTime);

        // Act
        String elapsedTime = orderUseCase.calculateElapsedTime(orderId, ownerId);

        // Assert
        assertEquals(expectedElapsedTime, elapsedTime);
        verify(orderPersistencePort, times(1)).getOrderById(orderId);
        verify(orderLogClientPort, times(1)).calculateElapsedTimeByOrderId(orderId);
    }

    @Test
    public void testCalculateElapsedTime_ShouldThrowOrderNotFoundException() {
        // Arrange
        Long orderId = 1L;
        Long ownerId = 2L;

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(null);

        // Act and Assert
        assertThrows(OrderNotFoundException.class, () -> {
            orderUseCase.calculateElapsedTime(orderId, ownerId);
        });
        verify(orderPersistencePort, times(1)).getOrderById(orderId);
        verifyNoInteractions(orderLogClientPort);
    }

    @Test
    public void testCalculateElapsedTime_ShouldThrowUnauthorizedOrderAccessException() {
        // Arrange
        Long orderId = 1L;
        Long ownerId = 2L;
        Long restaurantOwnerId = 3L;

        Order order = new Order();
        order.setIdRestaurant(new Restaurant());
        order.getIdRestaurant().setIdOwner(restaurantOwnerId);

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(order);

        // Act and Assert
        assertThrows(UnauthorizedOrderAccessException.class, () -> {
            orderUseCase.calculateElapsedTime(orderId, ownerId);
        });
        verify(orderPersistencePort, times(1)).getOrderById(orderId);
        verifyNoInteractions(orderLogClientPort);
    }

    @Test
    public void testCalculateAverageElapsedTimeByEmployee_ShouldReturnAverageElapsedTime() {
        // Arrange
        Long assignedEmployeeId = 1L;
        Long ownerId = 2L;
        String elapsedTimeStr1 = "PT2H30M";
        String elapsedTimeStr2 = "PT1H45M";
        Duration elapsedTime1 = Duration.parse(elapsedTimeStr1);
        Duration elapsedTime2 = Duration.parse(elapsedTimeStr2);
        List<Order> employeeOrders = new ArrayList<>();
        employeeOrders.add(createOrder(1L, "Delivered"));
        employeeOrders.add(createOrder(2L, "Delivered"));

        when(orderPersistencePort.getOrdersByEmployeeId(assignedEmployeeId)).thenReturn(employeeOrders);
        when(orderLogClientPort.calculateElapsedTimeByOrderId(1L)).thenReturn(elapsedTimeStr1);
        when(orderLogClientPort.calculateElapsedTimeByOrderId(2L)).thenReturn(elapsedTimeStr2);

        // Act
        String averageElapsedTime = orderUseCase.calculateAverageElapsedTimeByEmployee(assignedEmployeeId, ownerId);

        // Assert
        assertEquals("PT2H7M30S", averageElapsedTime);
        verify(orderPersistencePort, times(1)).getOrdersByEmployeeId(assignedEmployeeId);
        verify(orderLogClientPort, times(1)).calculateElapsedTimeByOrderId(1L);
        verify(orderLogClientPort, times(1)).calculateElapsedTimeByOrderId(2L);
    }

    @Test
    public void testCalculateAverageElapsedTimeByEmployee_ShouldThrowNoCompletedOrdersException() {
        // Arrange
        Long assignedEmployeeId = 1L;
        Long ownerId = 2L;
        List<Order> employeeOrders = new ArrayList<>();
        employeeOrders.add(createOrder(1L, "In Progress"));
        employeeOrders.add(createOrder(2L, "Pending"));

        when(orderPersistencePort.getOrdersByEmployeeId(assignedEmployeeId)).thenReturn(employeeOrders);

        // Act and Assert
        assertThrows(NoCompletedOrdersException.class, () -> {
            orderUseCase.calculateAverageElapsedTimeByEmployee(assignedEmployeeId, ownerId);
        });
        verify(orderPersistencePort, times(1)).getOrdersByEmployeeId(assignedEmployeeId);
        verifyNoInteractions(orderLogClientPort);
    }

    private Order createOrder(Long orderId, String status) {
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(status);
        return order;
    }


}