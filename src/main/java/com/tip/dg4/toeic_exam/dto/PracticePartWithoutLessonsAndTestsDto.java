package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticePartWithoutLessonsAndTestsDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    private String name;
    private String image;
    private String description;
}
