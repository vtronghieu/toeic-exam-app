package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.annotations.NotUUID;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDetailDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotUUID(message = TExamExceptionConstant.QUESTION_E006)
    private UUID questionId;

    @NotEmpty(message = TExamExceptionConstant.QUESTION_DETAIL_E001)
    @NotBlank(message = TExamExceptionConstant.QUESTION_DETAIL_E001)
    private String contentQuestion;

    @NotEmpty(message = TExamExceptionConstant.QUESTION_DETAIL_E002)
    @Size(min = 2, message = TExamExceptionConstant.QUESTION_DETAIL_E004)
    private Map<String, String> answers;

    @NotEmpty(message = TExamExceptionConstant.QUESTION_DETAIL_E003)
    @NotBlank(message = TExamExceptionConstant.QUESTION_DETAIL_E003)
    private String correctAnswer;
}
