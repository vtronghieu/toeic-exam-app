package com.tip.dg4.toeic_exam.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum QuestionType {
    PRACTICE("Practice"),
    TOEIC("Toeic"),
    VOCABULARY("Vocabulary"),
    GRAMMAR("Grammar"),
    UNDEFINED("Undefined");

    private String value;

    public static QuestionType getType(String value) {
        return Arrays.stream(QuestionType.values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst().orElse(UNDEFINED);
    }

    public static String getValueType(QuestionType type) {
        return Arrays.stream(QuestionType.values())
                .filter(questionType -> questionType.equals(type))
                .findFirst().orElse(UNDEFINED).value;
    }
}
