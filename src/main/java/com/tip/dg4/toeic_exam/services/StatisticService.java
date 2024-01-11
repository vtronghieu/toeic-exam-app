package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.StatisticDto;

import java.util.List;

public interface StatisticService {
    List<StatisticDto> getStatistics(int page, int size);

//    ReplyAnswerDto sendAnswer(SendAnswerDto sendAnswerDto);
//    List<Statistic> getTestHistoriesByTestIdAndUserId(UUID userId, UUID testId);
}
