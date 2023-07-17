package com.tip.dg4.toeic_exam.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
}
