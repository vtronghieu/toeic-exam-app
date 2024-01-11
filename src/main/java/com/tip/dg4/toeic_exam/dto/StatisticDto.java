package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;

    private String username;

    private String fullName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TExamConstant.DATE_TIME_FORMAT)
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TExamConstant.DATE_TIME_FORMAT)
    private LocalDateTime nearestTestDate;

    private int practiceProgress;
}