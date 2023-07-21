package com.tip.dg4.toeic_exam.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum QuestionLevel {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private String value;

    public static QuestionLevel getLevel(String value) {
        for (QuestionLevel level : QuestionLevel.values()) {
            if (value.equalsIgnoreCase(level.getValue())) {
                return level;
            }
        }
        return null;
    }

    public static String getValueLevel(QuestionLevel level) {
        for (QuestionLevel questionLevel : QuestionLevel.values()) {
            if (questionLevel.equals(level)) {
                return questionLevel.value;
            }
        }
        return null;
    }
}
