package com.tip.dg4.toeic_exam.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserAnswer {
    @Field(targetType = FieldType.STRING)
    private UUID questionId;
    private List<QuestionDetail> questionDetails;
    private String answerContent;
    private boolean isCorrect;
}
