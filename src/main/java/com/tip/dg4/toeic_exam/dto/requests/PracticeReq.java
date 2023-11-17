package com.tip.dg4.toeic_exam.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PracticeReq {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;

    @NotEmpty(message = TExamExceptionConstant.PRACTICE_E004)
    @NotBlank(message = TExamExceptionConstant.PRACTICE_E004)
    private String name;

    @NotEmpty(message = TExamExceptionConstant.PRACTICE_E002)
    @NotBlank(message = TExamExceptionConstant.PRACTICE_E002)
    private String type;

    @NotEmpty(message = TExamExceptionConstant.PRACTICE_E005)
    @NotBlank(message = TExamExceptionConstant.PRACTICE_E005)
    private String imageURL;
}
