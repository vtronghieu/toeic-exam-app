package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import jakarta.validation.Valid;
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
public class UserDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;

    @NotEmpty(message = TExamExceptionConstant.AUTH_E001)
    @NotBlank(message = TExamExceptionConstant.AUTH_E001)
    private String username;

    @NotEmpty(message = TExamExceptionConstant.AUTH_E002)
    @NotBlank(message = TExamExceptionConstant.AUTH_E002)
    private String password;

    @NotEmpty(message = TExamExceptionConstant.USER_E004)
    @NotBlank(message = TExamExceptionConstant.USER_E004)
    private String role;

    @JsonProperty(value = "isActive")
    private boolean isActive;

    @Valid
    private UserInfoDto userInfo;
}
