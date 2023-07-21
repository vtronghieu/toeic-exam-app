package com.tip.dg4.toeic_exam.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum QuestionType {
    PRACTICE("practice"),
    TOEIC("toeic"),
    VOCABULARY("vocabulary"),
    GRAMMAR("grammar");

    private String value;

    public static QuestionType getType(String value) {
        for (QuestionType type : QuestionType.values()) {
            if (value.equalsIgnoreCase(type.getValue())) {
                return type;
            }
        }
        return null;
    }

    public static String getValueType(QuestionType level) {
        for (QuestionType questionType : QuestionType.values()) {
            if (level.equals(questionType)) {
                return questionType.value;
            }
        }
        return null;
    }
}
