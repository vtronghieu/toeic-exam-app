package com.tip.dg4.toeic_exam.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN("Admin"),
    USER("User"),
    UNDEFINED("Undefined");

    private final String value;

    public static UserRole getRole(String value) {
        return Arrays.stream(UserRole.values())
                .filter(role -> value.equalsIgnoreCase(role.getValue()))
                .findFirst()
                .orElse(UNDEFINED);
    }

    public static String getValue(UserRole role) {
        return Arrays.stream(UserRole.values())
                .filter(role::equals)
                .findFirst()
                .orElse(UNDEFINED)
                .getValue();
    }
}