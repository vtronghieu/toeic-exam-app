package com.tip.dg4.toeic_exam.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtType {
    ACCESS_TOKEN,
    REFRESH_TOKEN
}