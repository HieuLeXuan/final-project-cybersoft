package com.example.riraproject.common.validation.annotation;

import com.example.riraproject.common.validation.validator.FieldNotNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FieldNotNullValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldNotNull {
    String message() default "Field cannot be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
