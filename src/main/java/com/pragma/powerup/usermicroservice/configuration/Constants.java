package com.pragma.powerup.usermicroservice.configuration;

public class Constants {



    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final Long CLIENT_ROLE_ID = 1L;
    public static final Long EMPLOYEE_ROLE_ID = 2L;
    public static final Long PROVIDER_ROLE_ID = 3L;
    public static final int MAX_PAGE_SIZE = 2;
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final String INVALID_USER_EXCEPTION = "Invalid user.";
    public static final String DUPLICATED_NAME_EXCEPTION = "Duplicated name";
    public static final String PHONE_NUMBER_FORMAT_EXCEPTION = "Phone number format invalid";
    public static final String BAD_REQUEST_EXCEPTION = "Bad request. Try again please";
    public static final String DISH_CREATED_MESSAGE = "Dish created successfully";
    public static final String NIT_MUST_BE_NUMERIC_EXCEPTION = "Nit must be numeric";
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
