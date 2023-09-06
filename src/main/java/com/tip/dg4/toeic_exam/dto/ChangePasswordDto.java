package com.tip.dg4.toeic_exam.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ChangePasswordDto {
    private UUID id;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
