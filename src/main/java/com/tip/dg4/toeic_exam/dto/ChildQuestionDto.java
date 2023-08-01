package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildQuestionDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID questionId;
    private String textQuestion;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String correctAnswer;
}
