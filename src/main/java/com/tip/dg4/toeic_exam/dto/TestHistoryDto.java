package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.models.QuestionType;
import com.tip.dg4.toeic_exam.models.TestHistoryStatus;
import com.tip.dg4.toeic_exam.models.UserAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestHistoryDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private QuestionType questionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID testId;
    private TestHistoryStatus status;
    private LocalDate date;
    private List<UserAnswer> userAnswers;
    private int totalQuestion;
}