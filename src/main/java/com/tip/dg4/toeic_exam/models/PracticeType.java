package com.tip.dg4.toeic_exam.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PracticeType {
    LISTEN("listen"),
    READ("read"),
    SPEAK("speak"),
    WRITE("write");

    private String value;

    public static PracticeType getType(String value) {
        for (PracticeType type : PracticeType.values()) {
            if (value.equalsIgnoreCase(type.value)) {
                return type;
            }
        }
        return null;
    }

    public static String getValueType(PracticeType practiceType) {
        for (PracticeType type : PracticeType.values()) {
            if (practiceType.equals(type)) {
                return type.value;
            }
        }
        return null;
    }
}
