package com.tip.dg4.toeic_exam.models;

import com.tip.dg4.toeic_exam.annotations.GenerateID;
import com.tip.dg4.toeic_exam.models.enums.QuestionLevel;
import com.tip.dg4.toeic_exam.models.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@AllArgsConstructor
@Builder
@Document(collection = "questions")
public class Question {
    @Id
    @GenerateID
    @Field(targetType = FieldType.STRING)
    private UUID id;

    @Field(targetType = FieldType.STRING)
    private QuestionType type;

    @Field(targetType = FieldType.STRING)
    private UUID objectTypeId;

    @Field(targetType = FieldType.STRING)
    private QuestionLevel level;

    private List<String> imageURLs;

    private String audioURL;

    private String transcript;

    private List<QuestionDetail> questionDetails;
}
