package com.als.anno;

import com.als.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {StateValidation.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface State {
    String message() default "state params error!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
