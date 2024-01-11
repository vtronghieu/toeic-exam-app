package com.tip.dg4.toeic_exam.dto.requests;

import com.tip.dg4.toeic_exam.annotations.NotUUID;
import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
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
public class LessonReq {
    private UUID id;

    @NotUUID(message = ExceptionConstant.LESSON_E003)
    private UUID partId;

    @NotEmpty(message = ExceptionConstant.LESSON_E004)
    @NotBlank(message = ExceptionConstant.LESSON_E004)
    private String name;
}
