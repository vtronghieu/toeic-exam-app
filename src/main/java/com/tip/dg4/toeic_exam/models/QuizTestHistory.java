package com.tip.dg4.toeic_exam.models;

import com.tip.dg4.toeic_exam.models.enums.QuestionType;
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
@Document(collection = "quiz_test_histories")
public class QuizTestHistory {
    @Id
    @Field(targetType = FieldType.STRING)
    private UUID id;
    @Field(targetType = FieldType.STRING)
    private UUID userId;
    @Field(targetType = FieldType.STRING)
    private UUID categoryId;
    @Field(targetType = FieldType.STRING)
    private QuestionType type;
    private List<UserAnswer> userAnswers;
}
