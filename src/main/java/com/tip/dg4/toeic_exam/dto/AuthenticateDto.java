package com.tip.dg4.toeic_exam.dto;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticateDto {
    @NotEmpty(message = TExamExceptionConstant.AUTH_E001)
    @NotBlank(message = TExamExceptionConstant.AUTH_E001)
    private String username;

    @NotEmpty(message = TExamExceptionConstant.AUTH_E002)
    @NotBlank(message = TExamExceptionConstant.AUTH_E002)
    private String password;
}
