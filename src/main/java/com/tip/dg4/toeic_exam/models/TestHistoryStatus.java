package com.tip.dg4.toeic_exam.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TestHistoryStatus {
    DONE("done"),
    TESTING("testing");

    private String value;

    public static TestHistoryStatus getType(String value) {
        for (TestHistoryStatus status : TestHistoryStatus.values()) {
            if (value.equalsIgnoreCase(status.getValue())) {
                return status;
            }
        }
        return null;
    }

    public static String getValueType(TestHistoryStatus level) {
        for (TestHistoryStatus TestHistoryStatus : TestHistoryStatus.values()) {
            if (level.equals(TestHistoryStatus)) {
                return TestHistoryStatus.value;
            }
        }
        return null;
    }
}
