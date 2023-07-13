package com.tip.dg4.toeic_exam.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "vocabulary_questions")
public class VocabularyQuestion {
    @Id
    @Field(targetType = FieldType.STRING)
    private UUID id;
    private String question;
    private List<String> answers;
    private String correctAnswer;
    @Field(targetType = FieldType.STRING)
    private UUID vocabularyId;
}
