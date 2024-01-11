package com.tip.dg4.toeic_exam.dto.requests;

import com.tip.dg4.toeic_exam.annotations.NotUUID;
import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
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

    @NotUUID(message = ExceptionConstant.TEST_E001)
    private UUID partId;

    @NotEmpty(message = ExceptionConstant.TEST_E002)
    @NotBlank(message = ExceptionConstant.TEST_E002)
    private String type;

    @NotEmpty(message = ExceptionConstant.TEST_E003)
    @NotBlank(message = ExceptionConstant.TEST_E003)
    private String name;

    @Valid
    private List<QuestionReq> questions;
}
