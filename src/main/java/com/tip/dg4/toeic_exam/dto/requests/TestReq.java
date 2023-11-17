package com.tip.dg4.toeic_exam.dto.requests;

import com.tip.dg4.toeic_exam.annotations.NotUUID;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import jakarta.validation.Valid;
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
public class TestReq {
    private UUID id;

    @NotUUID(message = TExamExceptionConstant.TEST_E001)
    private UUID partId;

    @NotEmpty(message = TExamExceptionConstant.TEST_E002)
    @NotBlank(message = TExamExceptionConstant.TEST_E002)
    private String type;

    @NotEmpty(message = TExamExceptionConstant.TEST_E003)
    @NotBlank(message = TExamExceptionConstant.TEST_E003)
    private String name;

    @Valid
    private List<QuestionReq> questions;
}
