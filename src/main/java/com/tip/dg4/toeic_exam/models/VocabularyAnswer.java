package com.tip.dg4.toeic_exam.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.UUID;
@Data
public class VocabularyAnswer {
    @Field(targetType = FieldType.STRING)
    private UUID questionId;
    private String userAnswer;
    private String correctAnswer;
    @JsonProperty(value = "isCorrect")
    private boolean isCorrect;
}