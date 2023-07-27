package com.tip.dg4.toeic_exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartTestWithoutUserAnswerAndFinishTimeDto {
    private String type;
    private String name;
}
