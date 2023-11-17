package com.tip.dg4.toeic_exam.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PracticeType {
    LISTEN("Listen"),
    READ("Read"),
    SPEAK("Speak"),
    WRITE("Write"),
    UNDEFINED("Undefined");

    private String value;

    public static PracticeType getType(String value) {
        return Arrays.stream(PracticeType.values())
                .filter(practiceType -> value.equalsIgnoreCase(practiceType.value))
                .findFirst()
                .orElse(UNDEFINED);
    }

    public static String getValueType(PracticeType type) {
        return Arrays.stream(PracticeType.values())
                .filter(type::equals)
                .findFirst()
                .orElse(UNDEFINED).value;
    }
}
