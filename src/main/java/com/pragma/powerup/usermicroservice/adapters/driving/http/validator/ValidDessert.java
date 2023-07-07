package com.pragma.powerup.usermicroservice.adapters.driving.http.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DessertValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDessert {
    String message() default "Invalid dessert";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
