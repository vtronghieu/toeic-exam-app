package com.tip.dg4.toeic_exam.dto.user;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @NotEmpty(message = ExceptionConstant.USER_E005)
    @NotBlank(message = ExceptionConstant.USER_E005)
    private String oldPassword;

    @NotEmpty(message = ExceptionConstant.USER_E006)
    @NotBlank(message = ExceptionConstant.USER_E006)
    private String newPassword;

    @NotEmpty(message = ExceptionConstant.USER_E007)
    @NotBlank(message = ExceptionConstant.USER_E007)
    private String confirmNewPassword;
}
