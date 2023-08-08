package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.TestHistoryDto;
import com.tip.dg4.toeic_exam.models.TestHistory;
import org.springframework.stereotype.Component;

@Component
public class TestHistoryMapper {
    public TestHistoryDto convertModelTesthistoryDto(TestHistory history) {
        TestHistoryDto testHistoryDto = new TestHistoryDto();

        testHistoryDto.setUserId(history.getUserId());
        testHistoryDto.setId(history.getId());
        testHistoryDto.setDate(history.getDate());
        testHistoryDto.setTestId(history.getTestId());
        testHistoryDto.setQuestionType(history.getQuestionType());
        testHistoryDto.setStatus(history.getStatus());
        testHistoryDto.setUserAnswers(history.getUserAnswers());

        return testHistoryDto;
    }
}
