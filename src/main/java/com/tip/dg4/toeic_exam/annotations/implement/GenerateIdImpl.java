package com.tip.dg4.toeic_exam.annotations.implement;

import com.tip.dg4.toeic_exam.annotations.GenerateID;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * A Spring Data MongoDB event listener class that generates IDs for MongoDB documents before they are converted and saved.
 * This class listens to the {@link org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent} event and
 * generates IDs for annotated fields based on the presence of the {@link org.springframework.data.annotation.Id} and
 * {@link com.tip.dg4.toeic_exam.annotations.GenerateID} annotations.
 */
@Component
public class GenerateIdImpl extends AbstractMongoEventListener<Object> {
    private static final String MODEL_PACKAGE = "com.tip.dg4.toeic_exam.models";

    /**
     * The method to generate IDs for MongoDB documents before conversion.
     *
     * @param event The BeforeConvertEvent containing the object to be converted.
     */
    @Override
    public void onBeforeConvert(@NonNull BeforeConvertEvent<Object> event) {
        Object source = event.getSource();

        this.generateID(source, isObjectCreated(source));
    }

    /**
     * Checks if the ID field is already set in the given object.
     *
     * @param source The object to check for the presence of an ID field.
     * @return true if the ID field is not set, false otherwise.
     * @throws IllegalArgumentException If the ID field is required but not present.
     */
    private boolean isObjectCreated(Object source) {
        Field idField = this.getIdField(source);

        if (Objects.isNull(idField)) {
            throw new IllegalArgumentException(source.getClass().getCanonicalName() + ": The ID field is required");
        }

        return Objects.isNull(this.getFieldValue(idField, source));
    }

    /**
     * Retrieves the ID field for the given object.
     *
     * @param source The object to find the ID field in.
     * @return The ID field of the object, null if not found.
     */
    private Field getIdField(Object source) {
        return Arrays.stream(source.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(Id.class)).findFirst().orElse(null);
    }

    /**
     * Gets the value of a field in the given object.
     *
     * @param field  The field to retrieve the value from.
     * @param source The object containing the field.
     * @return The value of the field, or null if it cannot be accessed.
     */
    private Object getFieldValue(Field field, Object source) {
        try {
            field.setAccessible(true);
            return field.get(source);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Generates IDs for the fields annotated with {@link org.springframework.data.annotation.Id} and
     * {@link com.tip.dg4.toeic_exam.annotations.GenerateID}.
     *
     * @param source          The object to generate IDs for.
     * @param isObjectCreated true if the object is already created, false otherwise.
     */
    private void generateID(Object source, boolean isObjectCreated) {
        for (Field field : source.getClass().getDeclaredFields()) {
            Object fieldValue = this.getFieldValue(field, source);
            if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GenerateID.class)) {
                if (!isObjectCreated && Objects.isNull(fieldValue)) {
                    throw new NullPointerException(field.getDeclaringClass().getCanonicalName() + ": Id can't be null");
                }
                this.setAutoGenerateID(field, source);
            } else {
                if (!isModelClassField(field)) continue;

                // Generate ID for sub-document
                if (Objects.nonNull(fieldValue)) {
                    if (fieldValue instanceof Collection<?>) {
                        for (Object subSource : (Collection<?>) fieldValue) {
                            this.generateID(subSource, isObjectCreated(subSource));
                        }
                    } else {
                        this.generateID(fieldValue, isObjectCreated(fieldValue));
                    }
                }
            }
        }
    }

    /**
     * Sets a UUID value for the given field if it is not already set.
     *
     * @param field  The field to set the UUID value for.
     * @param source The object containing the field.
     * @throws MappingException If an error occurs while setting the UUID field.
     */
    private void setAutoGenerateID(Field field, Object source) {
        field.setAccessible(true);
        boolean isAccessible = field.canAccess(source);

        try {
            if (Objects.isNull(field.get(source))) {
                field.set(source, UUID.randomUUID());
            }
        } catch (IllegalAccessException e) {
            throw new MappingException("Error setting UUID field", e);
        } finally {
            field.setAccessible(isAccessible);
        }
    }

    /**
     * Checks if a field is part of a model class within the specified package.
     *
     * @param field The field to check.
     * @return true if the field is part of a model class, false otherwise.
     */
    private boolean isModelClassField(Field field) {
        String fieldPackage = TExamConstant.EMPTY;
        Class<?> fieldClass = field.getType();

        if (field.getType().isPrimitive() || Enum.class.isAssignableFrom(fieldClass)) {
            return false;
        } else if (Collection.class.isAssignableFrom(fieldClass)) {
            Type fieldType = field.getGenericType();
            if (fieldType instanceof ParameterizedType parameterizedType) {
                Type[] types = parameterizedType.getActualTypeArguments();
                fieldClass = (Class<?>) types[0];
                fieldPackage = fieldClass.getPackageName();
            }
        } else {
            fieldPackage = fieldClass.getPackageName();
        }

        return fieldPackage.startsWith(MODEL_PACKAGE);
    }
}