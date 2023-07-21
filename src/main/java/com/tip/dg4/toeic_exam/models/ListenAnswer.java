package com.tip.dg4.toeic_exam.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListenAnswer {
    private String answerContent;
    private Integer correctPercent;
}
