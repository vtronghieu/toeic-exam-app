package com.tip.dg4.toeic_exam.models;

import com.tip.dg4.toeic_exam.annotations.GenerateID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDetail {
    @Id
    @GenerateID
    @Field(targetType = FieldType.STRING)
    private UUID id;

    @Field(targetType = FieldType.STRING)
    private UUID questionId;

    private String contentQuestion;

    private Map<String, String> answers;

    private String correctAnswer;
}
