package com.tip.dg4.toeic_exam.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionAnswers {
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String correctAnswer;
}
