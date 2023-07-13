package com.tip.dg4.toeic_exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestVocabularyAnswersDto {
    @Field(targetType = FieldType.STRING)
    private UUID questionId;
    private String userAnswer;
}
