package com.tip.dg4.toeic_exam.models;

import com.tip.dg4.toeic_exam.annotations.GenerateID;
import com.tip.dg4.toeic_exam.models.enums.QuestionType;
import com.tip.dg4.toeic_exam.models.enums.StatisticStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "statistic_details")
public class StatisticDetail {
    @Id
    @GenerateID
    @Field(targetType = FieldType.STRING)
    private UUID id;

    @Field(targetType = FieldType.STRING)
    private UUID statisticId;

    @Field(targetType = FieldType.STRING)
    private QuestionType questionType;

    private String moduleName;

    private String testName;

    private int progress;

    @Field(targetType = FieldType.STRING)
    private StatisticStatus statisticStatus;

    private LocalDateTime createDate;

    private LocalDateTime completeDate;

    private TestHistory testHistory;
}
