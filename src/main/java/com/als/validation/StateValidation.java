package com.als.validation;

import com.als.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        /**
         *
         * @param value 被校验的值
         *
         * @return
         */
        if (value == null) return false;

        return value.equals("已发布") || value.equals("草稿");
    }
}
