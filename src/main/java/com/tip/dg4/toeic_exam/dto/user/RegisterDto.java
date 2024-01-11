package com.tip.dg4.toeic_exam.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RegisterDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String role;
    private String surname;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private Integer age;
}
