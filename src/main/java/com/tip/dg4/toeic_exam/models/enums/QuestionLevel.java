package com.tip.dg4.toeic_exam.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum QuestionLevel {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard"),
    UNDEFINED("Undefined");

    private String value;

    public static QuestionLevel getLevel(String value) {
        return Arrays.stream(QuestionLevel.values())
                .filter(level -> level.value.equalsIgnoreCase(value))
                .findFirst().orElse(UNDEFINED);
    }

    public static String getValueLevel(QuestionLevel level) {
        return Arrays.stream(QuestionLevel.values())
                .filter(questionLevel -> questionLevel.equals(level))
                .findFirst().orElse(UNDEFINED).value;
    }
}
