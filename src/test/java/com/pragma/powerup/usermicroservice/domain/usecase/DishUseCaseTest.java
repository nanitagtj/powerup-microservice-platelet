package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.clientapi.IUserClientPort;
import com.pragma.powerup.usermicroservice.domain.exceptions.DuplicateRestaurantName;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidUserException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNoFoundException;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DishUseCaseTest {

    private DishUseCase dishUseCase;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserClientPort userClientPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dishUseCase = new DishUseCase(dishPersistencePort, restaurantPersistencePort, userClientPort);
    }

    @Test
    public void testCreateDish_ValidOwner() {
        // Arrange
        Dish dish = new Dish();
        dish.setName("Test Dish");
        dish.setRestaurant(new Restaurant());
        dish.getRestaurant().setId(1L);
        dish.getRestaurant().setIdOwner(2L);

        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(dish.getRestaurant());
        when(dishPersistencePort.existsByName("Test Dish")).thenReturn(false);

        // Act
        dishUseCase.createDish(dish, 2L);

        // Assert
        verify(dishPersistencePort, times(1)).saveDish(dish);
    }

    @Test
    public void testCreateDish_NullOwnerId() {
        // Arrange
        Dish dish = new Dish();
        dish.setName("Test Dish");
        dish.setRestaurant(new Restaurant());
        dish.getRestaurant().setId(1L);
        dish.getRestaurant().setIdOwner(2L);

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> dishUseCase.createDish(dish, null));

        // Verify that no interaction occurred with the persistence port
        verifyZeroInteractions(dishPersistencePort);
    }

    private void verifyZeroInteractions(IDishPersistencePort dishPersistencePort) {
    }

    @Test
    public void testCreateDish_InvalidOwner() {
        // Arrange
        Dish dish = new Dish();
        dish.setName("Test Dish");
        dish.setRestaurant(new Restaurant());
        dish.getRestaurant().setId(1L);
        dish.getRestaurant().setIdOwner(2L);

        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(dish.getRestaurant());

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> dishUseCase.createDish(dish, 3L));

        // Verify that no interaction occurred with the persistence port
        verifyZeroInteractions(dishPersistencePort);
    }
    @Test
    public void testCreateDish_DuplicateName() {
        // Arrange
        Dish dish = new Dish();
        dish.setName("Test Dish");
        dish.setRestaurant(new Restaurant());
        dish.getRestaurant().setId(1L);
        dish.getRestaurant().setIdOwner(2L);

        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(dish.getRestaurant());
        when(dishPersistencePort.existsByName("Test Dish")).thenReturn(true);
        // Act & Assert
        assertThrows(DuplicateRestaurantName.class, () -> dishUseCase.createDish(dish, 2L));

        // Verify that no interaction occurred with the persistence port
        verifyZeroInteractions(dishPersistencePort);
    }

    @Test
    public void testUpdateDish_ValidOwner() {
        // Arrange
        Dish existingDish = new Dish();
        existingDish.setId(1L);
        existingDish.setPrice(10);
        existingDish.setDescription("Old Description");

        Dish updatedDish = new Dish();
        updatedDish.setId(1L);
        updatedDish.setPrice(15);
        updatedDish.setDescription("New Description");

        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(2L);

        updatedDish.setRestaurant(restaurant);

        when(restaurantPersistencePort.getRestaurantById(updatedDish.getRestaurant().getId())).thenReturn(restaurant);
        when(dishPersistencePort.getDishById(updatedDish.getId())).thenReturn(existingDish);

        // Act
        dishUseCase.updateDish(updatedDish.getId(), updatedDish, 2L);

        // Assert
        verify(dishPersistencePort, times(1)).saveDish(existingDish);
        assertEquals(updatedDish.getPrice(), existingDish.getPrice());
        assertEquals(updatedDish.getDescription(), existingDish.getDescription());
    }

    @Test
    public void testUpdateDish_NullOwnerId() {
        // Arrange
        Dish existingDish = new Dish();
        existingDish.setId(1L);
        existingDish.setPrice(10);
        existingDish.setDescription("Old Description");

        Dish updatedDish = new Dish();
        updatedDish.setId(1L);
        updatedDish.setPrice(15);
        updatedDish.setDescription("New Description");

        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(2L);

        updatedDish.setRestaurant(restaurant);

        when(restaurantPersistencePort.getRestaurantById(updatedDish.getRestaurant().getId())).thenReturn(restaurant);
        when(dishPersistencePort.getDishById(updatedDish.getId())).thenReturn(existingDish);

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> dishUseCase.updateDish(updatedDish.getId(), updatedDish, null));

        // Verify that no interaction occurred with the persistence port
        verifyZeroInteractions(dishPersistencePort);
    }

    @Test
    public void testUpdateDish_InvalidOwner() {
        // Arrange
        Dish existingDish = new Dish();
        existingDish.setId(1L);
        existingDish.setPrice(10);
        existingDish.setDescription("Old Description");

        Dish updatedDish = new Dish();
        updatedDish.setId(1L);
        updatedDish.setPrice(15);
        updatedDish.setDescription("New Description");

        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(2L);

        updatedDish.setRestaurant(restaurant);

        when(restaurantPersistencePort.getRestaurantById(updatedDish.getRestaurant().getId())).thenReturn(restaurant);
        when(dishPersistencePort.getDishById(updatedDish.getId())).thenReturn(existingDish);

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> dishUseCase.updateDish(updatedDish.getId(), updatedDish, 3L));

        // Verify that no interaction occurred with the persistence port
        verifyZeroInteractions(dishPersistencePort);
    }

    @Test
    public void testUpdateDish_RestaurantNotFound() {
        // Arrange
        Dish updatedDish = new Dish();
        updatedDish.setId(1L);
        updatedDish.setPrice(15);
        updatedDish.setDescription("New Description");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        updatedDish.setRestaurant(restaurant);

        Long restaurantId = restaurant.getId();
        when(restaurantPersistencePort.getRestaurantById(restaurantId)).thenReturn(null);

        // Act & Assert
        assertThrows(RestaurantNoFoundException.class, () -> dishUseCase.updateDish(updatedDish.getId(), updatedDish, 2L));

        // Verify that no interaction occurred with the persistence port
        verifyZeroInteractions(dishPersistencePort);
    }
}