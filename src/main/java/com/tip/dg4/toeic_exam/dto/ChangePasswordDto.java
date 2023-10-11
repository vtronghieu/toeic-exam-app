package com.tip.dg4.toeic_exam.dto;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @NotEmpty(message = TExamExceptionConstant.USER_E005)
    @NotBlank(message = TExamExceptionConstant.USER_E005)
    private String oldPassword;

    @NotEmpty(message = TExamExceptionConstant.USER_E006)
    @NotBlank(message = TExamExceptionConstant.USER_E006)
    private String newPassword;

    @NotEmpty(message = TExamExceptionConstant.USER_E007)
    @NotBlank(message = TExamExceptionConstant.USER_E007)
    private String confirmNewPassword;
}
