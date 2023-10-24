package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.annotations.NotUUID;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.models.Example;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ContentDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotUUID(message = TExamExceptionConstant.CONTENT_E003)
    private UUID lessonId;

    @NotEmpty(message = TExamExceptionConstant.CONTENT_E001)
    @NotBlank(message = TExamExceptionConstant.CONTENT_E001)
    private String title;

    @NotNull(message = TExamExceptionConstant.CONTENT_E002)
    private Object content;

    private List<Example> examples;
}
