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

    public static TestHistoryStatus getStatus(String status) {
        for (TestHistoryStatus testHistoryStatus : TestHistoryStatus.values()) {
            if (status.equalsIgnoreCase(testHistoryStatus.getValue())) {
                return testHistoryStatus;
            }
        }
        return null;
    }

    public static String getValueStatus(TestHistoryStatus status) {
        for (TestHistoryStatus testHistoryStatus : TestHistoryStatus.values()) {
            if (status.equals(testHistoryStatus)) {
                return testHistoryStatus.value;
            }
        }
        return null;
    }
}
