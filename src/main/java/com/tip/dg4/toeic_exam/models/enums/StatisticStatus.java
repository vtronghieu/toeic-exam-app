package com.tip.dg4.toeic_exam.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum StatisticStatus {
    DONE("Done"),
    TESTING("Testing"),
    UNDEFINED("Undefined");

    private String value;

    public static StatisticStatus getStatus(String status) {
        return Arrays.stream(StatisticStatus.values())
                .filter(statisticStatus -> status.equalsIgnoreCase(statisticStatus.value))
                .findFirst()
                .orElse(StatisticStatus.UNDEFINED);
    }

    public static String getValueStatus(StatisticStatus status) {
        return Arrays.stream(StatisticStatus.values())
                .filter(status::equals)
                .findFirst()
                .orElse(StatisticStatus.UNDEFINED).value;
    }
}
