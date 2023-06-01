package com.tip.dg4.toeic_exam.config;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.UUID;

/**
 * This class is an Event Listener that handles the automatic generation
 * of IDs before converting and saving an object to the database.
 */
@Component
public class IDGenerationConfig extends AbstractMongoEventListener<Object> {
    /**
     * Automatically generates an ID of type UUID for the object before
     * it is converted and saved to the database.
     *
     * @param event: the event before converting and saving the object to the database
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object entity = event.getSource();
        ReflectionUtils.doWithFields(entity.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            if (field.isAnnotationPresent(Id.class) && field.getType().equals(UUID.class) && field.get(entity) == null) {
                field.set(entity, UUID.randomUUID());
            }
        });
    }
}
