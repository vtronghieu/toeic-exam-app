package com.tip.dg4.toeic_exam.dto;

import com.tip.dg4.toeic_exam.models.VocabularyAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyAnswerDto {
    @Field(targetType = FieldType.STRING)
    private UUID userId;
    private List<VocabularyAnswer> vocabularyAnswers;
}