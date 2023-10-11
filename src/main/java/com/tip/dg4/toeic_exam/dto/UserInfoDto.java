package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;

    @NotEmpty(message = TExamExceptionConstant.USER_INFO_E001)
    @NotBlank(message = TExamExceptionConstant.USER_INFO_E001)
    private String surname;

    @NotEmpty(message = TExamExceptionConstant.USER_INFO_E002)
    @NotBlank(message = TExamExceptionConstant.USER_INFO_E002)
    private String name;

    @NotEmpty(message = TExamExceptionConstant.USER_INFO_E003)
    @NotBlank(message = TExamExceptionConstant.USER_INFO_E003)
    @Email(message = TExamExceptionConstant.USER_INFO_E003)
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TExamConstant.DATE_FORMAT)
    private LocalDate dateOfBirth;

    private int age;

    private String address;

    private String phone;
}
