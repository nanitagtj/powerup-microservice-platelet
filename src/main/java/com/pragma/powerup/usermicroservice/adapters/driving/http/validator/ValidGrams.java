package com.pragma.powerup.usermicroservice.adapters.driving.http.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GramsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGrams {
    String message() default "Invalid grams value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
