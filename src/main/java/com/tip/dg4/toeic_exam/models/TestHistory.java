package com.tip.dg4.toeic_exam.models;

import com.tip.dg4.toeic_exam.models.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "test_histories")
public class TestHistory {
    @Id
    @Field(targetType = FieldType.STRING)
    private UUID id;
    @Field(targetType = FieldType.STRING)
    private QuestionType questionType;
    @Field(targetType = FieldType.STRING)
    private UUID userId;
    @Field(targetType = FieldType.STRING)
    private UUID testId;
    private TestHistoryStatus status;
    private LocalDate date;
    private List<UserAnswer> userAnswers;
}
