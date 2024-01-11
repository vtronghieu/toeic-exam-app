package com.tip.dg4.toeic_exam.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AccountDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    private String username;
    private String password;
    private String role;
    @JsonProperty(value = "isActive")
    private boolean isActive;
}
