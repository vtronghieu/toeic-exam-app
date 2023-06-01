package com.tip.dg4.toeic_exam.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
    private String username;
    private String password;
}
