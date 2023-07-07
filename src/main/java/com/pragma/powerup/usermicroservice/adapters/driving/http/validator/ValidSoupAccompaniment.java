package com.pragma.powerup.usermicroservice.adapters.driving.http.validator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SoupAccompanimentValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSoupAccompaniment {
    String message() default "Invalid soup accompaniment";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
