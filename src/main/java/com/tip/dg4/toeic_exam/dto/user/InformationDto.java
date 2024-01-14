package com.tip.dg4.toeic_exam.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformationDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID userId;

    private String username;

    private String role;
}
