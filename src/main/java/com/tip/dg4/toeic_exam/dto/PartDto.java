package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotEmpty(message = TExamExceptionConstant.PART_E003)
    @NotBlank(message = TExamExceptionConstant.PART_E003)
    private UUID practiceId;

    @NotEmpty(message = TExamExceptionConstant.PART_E004)
    @NotBlank(message = TExamExceptionConstant.PART_E004)
    private String name;

    @NotEmpty(message = TExamExceptionConstant.PART_E005)
    @NotBlank(message = TExamExceptionConstant.PART_E005)
    private String imageURL;

    private String description;

    private List<LessonDto> lessons;

    private List<TestDto> tests;
}
