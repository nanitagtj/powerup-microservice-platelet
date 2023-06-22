package com.pragma.powerup.usermicroservice.configuration;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
    public static final String EMPLOYEERESTAURANT_CREATED_MESSAGE = "Your assigned employee was added successfully";
    public static final String ORDER_NOT_FOUND_EXCEPTION = "Order not found";
    public static final String ORDER_UPDATED_MESSAGE = "Order updated";
    public static final String EMPLOYEE_NOT_ASSIGNED_EXCEPTION = "Employee not assigned to this restaurant";
    public static final String EMPLOYEE_ASSIGNED_EXCEPTION = "The employee is already assigned to other restaurant";
    public static final String IS_OWNER_EXCEPTION = "You're not the owner of this restaurant";
    public static final String SAME_RESTAURANT_EXCEPTION = "The employee is already assigned to this restaurant";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final String INVALID_USER_EXCEPTION = "Invalid user.";
    public static final String DUPLICATED_NAME_EXCEPTION = "Duplicated name";
    public static final Object DISH_UPDATED_MESSAGE = "Updated dish";
    public static final Object DISH_UPDATED_STATUS_MESSAGE = "Status updated";
    public static final String PHONE_NUMBER_FORMAT_EXCEPTION = "Phone number format invalid";
    public static final String ORDER_CREATED_MESSAGE = "You're order was created successfully";
    public static final String BAD_REQUEST_EXCEPTION = "Bad request. Try again please";
    public static final String DISH_CREATED_MESSAGE = "Dish created successfully";
    public static final String NIT_MUST_BE_NUMERIC_EXCEPTION = "Nit must be numeric";
    public static final String ORDER_IN_PROGRESS_EXCEPTION = "You have an order in progress";
    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials or role not allowed";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
