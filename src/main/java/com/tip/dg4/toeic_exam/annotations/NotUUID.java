package com.tip.dg4.toeic_exam.annotations;

import com.tip.dg4.toeic_exam.annotations.implement.NotUuidImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NotUuidImpl.class)
public @interface NotUUID {
    String message() default "com.tip.dg4.toeic_exam.annotations.NotUUID.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}