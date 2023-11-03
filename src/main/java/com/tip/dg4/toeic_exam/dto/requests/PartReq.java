package com.tip.dg4.toeic_exam.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.annotations.NotUUID;
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
public class PartReq {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;

    @NotUUID(message = TExamExceptionConstant.PART_E003)
    private UUID practiceId;

    @NotEmpty(message = TExamExceptionConstant.PART_E004)
    @NotBlank(message = TExamExceptionConstant.PART_E004)
    private String name;

    @NotEmpty(message = TExamExceptionConstant.PART_E005)
    @NotBlank(message = TExamExceptionConstant.PART_E005)
    private String imageURL;

    private String description;
}