package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtProvider;
import com.pragma.powerup.usermicroservice.domain.api.IEmployeeRestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IUserClientPort;
import com.pragma.powerup.usermicroservice.domain.exceptions.EmployeeAssignedException;
import com.pragma.powerup.usermicroservice.domain.exceptions.EmployeeAssignedToTheSameRestaurantException;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidUserException;
import com.pragma.powerup.usermicroservice.domain.exceptions.IsOwnerException;
import com.pragma.powerup.usermicroservice.domain.model.EmployeeRestaurant;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeRestaurantUseCase implements IEmployeeRestaurantServicePort {

    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserClientPort userClientPort;

    private static final Long EMPLOYEE_ROLE_ID = 3L;
    @Autowired
    JwtProvider jwtProvider;

    public EmployeeRestaurantUseCase(IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IUserClientPort userClientPort) {
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userClientPort = userClientPort;
    }

    @Override
    public void assignEmployeeToRestaurant(EmployeeRestaurant employeeRestaurant, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        validateEmployeeNotAssignedToOtherRestaurant(employeeRestaurant.getEmployeeId(), employeeRestaurant.getRestaurant().getId());
        UserResponseDto user = userClientPort.getUserById(employeeRestaurant.getEmployeeId(), token);
        validateEmployeeIsEmployeeType(user, token);
        validateUserIsOwnerOfRestaurant(token, employeeRestaurant.getRestaurant().getId());
        validateEmployeeNotAlreadyAssigned(employeeRestaurant.getEmployeeId(), employeeRestaurant.getRestaurant().getId());

        Restaurant restaurant = employeeRestaurant.getRestaurant();
        if (restaurant == null) {
            throw new IllegalArgumentException();
        }

        Long restaurantId = restaurant.getId();
        if (restaurantId == null) {
            throw new IllegalArgumentException();
        }

        employeeRestaurantPersistencePort.saveEmployeeRestaurant(employeeRestaurant);
    }

    private void validateEmployeeNotAssignedToOtherRestaurant(Long employeeId, Long restaurantId) {
        boolean isAssignedToOtherRestaurant = employeeRestaurantPersistencePort.isEmployeeAssignedToOtherRestaurant(employeeId, restaurantId);
        if (isAssignedToOtherRestaurant) {
            throw new EmployeeAssignedException();
        }
    }

    private void validateEmployeeIsEmployeeType(UserResponseDto userId, String token) {
        if (userId == null) {
            throw new InvalidUserException();
        }

        Long userRoleId = userId.getIdRole();
        UserResponseDto roleUser = userClientPort.getUserById(userRoleId, token);

        if (roleUser == null || !EMPLOYEE_ROLE_ID.equals(roleUser.getId())) {
            throw new InvalidUserException();
        }
    }

    private void validateUserIsOwnerOfRestaurant(String token, Long restaurantId) {
        Long ownerId = restaurantPersistencePort.getRestaurantOwnerId(restaurantId);
        Long userId = jwtProvider.getUserIdFromToken(token);
        if (!ownerId.equals(userId)) {
            throw new IsOwnerException();
        }
    }

    private void validateEmployeeNotAlreadyAssigned(Long employeeId, Long restaurantId) {
        boolean isEmployeeAlreadyAssigned = employeeRestaurantPersistencePort.isEmployeeAssignedToRestaurant(employeeId, restaurantId);
        if (isEmployeeAlreadyAssigned) {
            throw new EmployeeAssignedToTheSameRestaurantException();
        }
    }
}
