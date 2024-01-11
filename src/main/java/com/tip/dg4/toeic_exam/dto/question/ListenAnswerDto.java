package com.tip.dg4.toeic_exam.dto.question;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListenAnswerDto {
    private String answerContent;
    private Integer correctPercent;
}
