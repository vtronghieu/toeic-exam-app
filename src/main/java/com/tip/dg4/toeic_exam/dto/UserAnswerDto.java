package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserAnswerDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID questionId;
    private String optionAnswer;
    private ListenAnswerDto listenAnswer;
    private String writeAnswer;
    private Boolean isCorrect;
}
