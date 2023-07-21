package com.tip.dg4.toeic_exam.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserAnswer {
    @Field(targetType = FieldType.STRING)
    private UUID questionId;
    private String optionAnswer;
    private ListenAnswer listenAnswer;
    private String writeAnswer;
    private Boolean isCorrect;
}
