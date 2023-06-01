package com.tip.dg4.toeic_exam.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AccountRole {
    USER("user"),
    ADMIN("admin");

    private String value;

    public static AccountRole getType(String value) {
        for (AccountRole type : AccountRole.values()) {
            if (value.equalsIgnoreCase(type.getValue())) {
                return type;
            }
        }
        return null;
    }
}
