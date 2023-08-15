package com.tip.dg4.toeic_exam.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AccountRole {
    USER("user"),
    ADMIN("admin"),
    UNDEFINED("undefined");

    private String value;

    public static AccountRole getRole(String value) {
        for (AccountRole type : AccountRole.values()) {
            if (value.equalsIgnoreCase(type.getValue())) {
                return type;
            }
        }
        return null;
    }

    public static String getValueRole(AccountRole role) {
        return Arrays.stream(AccountRole.values())
                .filter(role::equals)
                .map(AccountRole::getValue)
                .findFirst().orElse(UNDEFINED.getValue());
    }
}
