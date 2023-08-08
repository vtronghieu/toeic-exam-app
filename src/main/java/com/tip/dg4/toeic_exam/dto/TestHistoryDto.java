package com.tip.dg4.toeic_exam.dto;

import com.tip.dg4.toeic_exam.models.QuestionType;
import com.tip.dg4.toeic_exam.models.TestHistoryStatus;
import com.tip.dg4.toeic_exam.models.UserAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestHistoryDto {
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
    private Date date;
    private List<UserAnswer> userAnswers;
    private int totalQuestion;

}