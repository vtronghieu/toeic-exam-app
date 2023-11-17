package com.tip.dg4.toeic_exam.annotations.implement;

import com.tip.dg4.toeic_exam.annotations.NotUUID;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.UUID;

/**
 * A constraint validator that ensures that a field is not a valid UUID.
 * This validator can be used to validate fields that are not supposed to be UUIDs, such as a user's name or email address.<br/>
 * To use this validator, you must annotate the field with the `@NotUUID` annotation.<br/>
 * When the validator is executed, it will check if the field value is a valid UUID.
 * If it is, the validator will fail and the validation will fail.
 */
public class NotUuidImpl implements ConstraintValidator<NotUUID, UUID> {
    /**
     * Validates whether the given UUID is not a valid UUID.
     *
     * @param uuid                       The UUID to validate.
     * @param constraintValidatorContext The constraint validator context.
     * @return true if the UUID is not valid, false otherwise.
     */
    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(uuid) || uuid.toString().matches(TExamConstant.UUID_REGEX);
    }
}
