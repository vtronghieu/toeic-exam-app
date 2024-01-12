package com.tip.dg4.toeic_exam.annotations.implement;

import com.tip.dg4.toeic_exam.annotations.GenerateID;
import com.tip.dg4.toeic_exam.utils.ConfigUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
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
    /**
     * The method to generate IDs for MongoDB documents before conversion.
     *
     * @param event The BeforeConvertEvent containing the object to be converted.
     */
    @Override
    public void onBeforeConvert(@NonNull BeforeConvertEvent<Object> event) {
        Object source = event.getSource();

        this.generateID(source, ConfigUtil.isObjectCreated(source));
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
            Object fieldValue = ConfigUtil.getFieldValue(field, source);
            if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GenerateID.class)) {
                if (!isObjectCreated && Objects.isNull(fieldValue)) {
                    throw new NullPointerException(field.getDeclaringClass().getCanonicalName() + ": Id can't be null");
                }
                this.setAutoGenerateID(field, source);
            } else {
                if (!ConfigUtil.isModelClassField(field)) continue;

                // Generate ID for sub-document
                if (Objects.nonNull(fieldValue)) {
                    if (fieldValue instanceof Collection<?>) {
                        for (Object subSource : (Collection<?>) fieldValue) {
                            this.generateID(subSource, ConfigUtil.isObjectCreated(subSource));
                        }
                    } else {
                        this.generateID(fieldValue, ConfigUtil.isObjectCreated(fieldValue));
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
}