package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IEmployeeRestaurantEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRestaurantRepository;
import com.pragma.powerup.usermicroservice.domain.model.EmployeeRestaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IEmployeeRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EmployeeRestaurantMysqlAdapter implements IEmployeeRestaurantPersistencePort {
    private final IEmployeeRestaurantRepository employeeRestaurantRepository;
    private final IEmployeeRestaurantEntityMapper employeeRestaurantEntityMapper;
    @Override
    public EmployeeRestaurant saveEmployeeRestaurant(EmployeeRestaurant employeeRestaurant) {
        EmployeeRestaurantEntity entity = employeeRestaurantEntityMapper.toEntity(employeeRestaurant);
        EmployeeRestaurantEntity savedEntity = employeeRestaurantRepository.save(entity);
        return employeeRestaurantEntityMapper.toEmployeeRestaurant(savedEntity);
    }

    @Override
    public boolean isEmployeeAssignedToOtherRestaurant(Long employeeId, Long restaurantId) {
        EmployeeRestaurantEntity existingAssignment = employeeRestaurantRepository.findByEmployeeId(employeeId);
        return existingAssignment != null && !existingAssignment.getRestaurant().getId().equals(restaurantId);
    }

    @Override
    public boolean isEmployeeAssignedToRestaurant(Long employeeId, Long restaurantId) {
        EmployeeRestaurantEntity existingAssignment = employeeRestaurantRepository.findByEmployeeIdAndRestaurantId(employeeId, restaurantId);
        return existingAssignment != null;
    }

    @Override
    public EmployeeRestaurantEntity getRestaurantEmployee(Long employeeId) {
        return employeeRestaurantRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<EmployeeRestaurant> getEmployeeRestaurantsByRestaurantId(Long restaurantId) {
        List<EmployeeRestaurantEntity> entities = employeeRestaurantRepository.findByRestaurantId(restaurantId);
        return entities.stream()
                .map(employeeRestaurantEntityMapper::toEmployeeRestaurant)
                .collect(Collectors.toList());
    }
}
