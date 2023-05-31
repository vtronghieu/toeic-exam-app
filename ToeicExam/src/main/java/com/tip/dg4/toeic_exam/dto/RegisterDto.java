package com.tip.dg4.toeic_exam.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDto {
    private String username;
    private String password;
    private String confirmPassword;
}
