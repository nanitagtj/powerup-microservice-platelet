package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IUserClientPort;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidUserException;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.usermicroservice.domain.validations.NullUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserClientPort userClientPort;

    private IRestaurantServicePort restaurantUseCase;

    private static final Long OWNER_ROLE_ID = 2L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, userClientPort);
    }

    @Test
    void createRestaurant_ValidOwner_ShouldSaveRestaurant() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(1L);
        restaurant.setName("Restaurant 1");

        UserResponseDto validOwner = new UserResponseDto();
        validOwner.setIdRole(OWNER_ROLE_ID);

        String authorizationHeader = "Bearer token";

        when(userClientPort.getUserById(restaurant.getIdOwner(), authorizationHeader)).thenReturn(validOwner);

        // Act
        assertDoesNotThrow(() -> restaurantUseCase.createRestaurant(restaurant, authorizationHeader));

        // Assert
        verify(userClientPort, times(1)).getUserById(restaurant.getIdOwner(), authorizationHeader);
        verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurant);
    }

    @Test
    void createRestaurant_InvalidOwner_ShouldThrowInvalidUserException() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(1L);
        restaurant.setName("Restaurant 1");

        UserResponseDto invalidOwner = new UserResponseDto();
        invalidOwner.setIdRole(3L);

        String authorizationHeader = "Bearer token";

        when(userClientPort.getUserById(restaurant.getIdOwner(), authorizationHeader)).thenReturn(invalidOwner);

        // Act and Assert
        InvalidUserException exception = assertThrows(InvalidUserException.class,
                () -> restaurantUseCase.createRestaurant(restaurant, authorizationHeader));

        assertEquals("Invalid user: User is not a valid owner.", exception.getMessage());

        verify(userClientPort, times(1)).getUserById(restaurant.getIdOwner(), authorizationHeader);
        verify(restaurantPersistencePort, never()).saveRestaurant(restaurant);
    }

    @Test
    void createRestaurant_NullUser_ShouldThrowNullUserException() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(1L);
        restaurant.setName("Restaurant 1");

        String authorizationHeader = "Bearer token";

        when(userClientPort.getUserById(restaurant.getIdOwner(), authorizationHeader)).thenReturn(null);

        // Act and Assert
        NullUserException exception = assertThrows(NullUserException.class,
                () -> restaurantUseCase.createRestaurant(restaurant, authorizationHeader));

        assertThat(exception.getMessage(), is(nullValue()));

        verify(userClientPort, times(1)).getUserById(restaurant.getIdOwner(), authorizationHeader);
        verify(restaurantPersistencePort, never()).saveRestaurant(restaurant);
    }



}