package com.tip.dg4.toeic_exam.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizeDto {
    private TokenDto tokens;

    private InformationDto information;
}

