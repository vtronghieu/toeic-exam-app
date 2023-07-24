package com.tip.dg4.toeic_exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticePartWithoutLessonsAndTestsDto {
    private String name;
    private String image;
    private String description;
}
