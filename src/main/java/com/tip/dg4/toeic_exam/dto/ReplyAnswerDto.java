package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyAnswerDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID questionId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID childQuestionId;
    private String userAnswer;
    private String correctAnswer;
    private boolean isCorrect;
}
