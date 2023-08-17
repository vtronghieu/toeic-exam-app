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
@Document(collection = "questions")
public class Question {
    @Id
    @Field(targetType = FieldType.STRING)
    private UUID id;
    @Field(targetType = FieldType.STRING)
    private QuestionType type;
    @Field(targetType = FieldType.STRING)
    private UUID objectTypeId;
    @Field(targetType = FieldType.STRING)
    private QuestionLevel level;
    private String audioQuestion;
    private String transcript;
    private List<String> imageUrls;
}
