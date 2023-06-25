package com.pragma.powerup.usermicroservice.configuration;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NitMustBeNumericException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.PhoneNumberFormatException;
import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

import static com.pragma.powerup.usermicroservice.configuration.Constants.*;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(NoDataFoundException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NO_DATA_FOUND_MESSAGE));
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Map<String, String>> handleInvalidUserException(InvalidUserException invalidUserException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_USER_EXCEPTION));
    }

    @ExceptionHandler(DuplicateRestaurantName.class)
    public ResponseEntity<Map<String, String>> handleDuplicateRestaurantNameException(DuplicateRestaurantName duplicateRestaurantName) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DUPLICATED_NAME_EXCEPTION));
    }

    @ExceptionHandler(NitMustBeNumericException.class)
    public ResponseEntity<Map<String, String>> handleNitMustBeNumericException(NitMustBeNumericException nitMustBeNumericException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NIT_MUST_BE_NUMERIC_EXCEPTION));
    }

    @ExceptionHandler(PhoneNumberFormatException.class)
    public ResponseEntity<Map<String, String>> handlePhoneNumberFormatException(PhoneNumberFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, PHONE_NUMBER_FORMAT_EXCEPTION));
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errorMap.put(propertyPath, message);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMap);
    }

    @ExceptionHandler(OrderInProgressException.class)
    public ResponseEntity<Map<String, String>> handleOrderInProgressException(OrderInProgressException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_IN_PROGRESS_EXCEPTION));
    }
    @ExceptionHandler(EmployeeAssignedException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeAssignedException(EmployeeAssignedException employeeAssignedException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, EMPLOYEE_ASSIGNED_EXCEPTION));
    }
    @ExceptionHandler(IsOwnerException.class)
    public ResponseEntity<Map<String, String>> handleIsOwnerException(IsOwnerException isOwnerException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, IS_OWNER_EXCEPTION));
    }
    @ExceptionHandler(EmployeeAssignedToTheSameRestaurantException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeAssignedToTheSameRestaurantException(EmployeeAssignedToTheSameRestaurantException employeeAssignedToTheSameRestaurantException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, SAME_RESTAURANT_EXCEPTION));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(OrderNotFoundException orderNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_FOUND_EXCEPTION));
    }
    @ExceptionHandler(OrderNoFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNoFoundException(OrderNoFoundException orderNoFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_FOUND_EXCEPTION));
    }

    @ExceptionHandler(EmployeeNotAssignedException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeNotAssignedException(EmployeeNotAssignedException employeeNotAssignedException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, EMPLOYEE_NOT_ASSIGNED_EXCEPTION));
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    public ResponseEntity<Map<String, String>> handleInvalidOrderStatusException(InvalidOrderStatusException invalidOrderStatusException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_ORDER_STATUS_EXCEPTION));
    }

    @ExceptionHandler(InvalidPinException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPinException(InvalidPinException invalidPinException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_PIN_EXCEPTION));
    }
    @ExceptionHandler(OrderMustBeInReadyStatus.class)
    public ResponseEntity<Map<String, String>> handleOrderMustBeInReadyStatus(OrderMustBeInReadyStatus orderMustBeInReadyStatus) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, STATUS_READY_EXCEPTION));
    }

    @ExceptionHandler(AwaitingOrderStatusException.class)
    public ResponseEntity<Map<String, String>> handleAwaitingOrderStatusException(AwaitingOrderStatusException awaitingOrderStatusException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, STATUS_AWAITING_EXCEPTION));
    }
    @ExceptionHandler(UnauthorizedOrderCancellationException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedOrderCancellationException(UnauthorizedOrderCancellationException unauthorizedOrderCancellationException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, UNAUTHORIZED_CLIENT_EXCEPTION));
    }

}
