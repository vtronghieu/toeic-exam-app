package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.SendAnswerDto;
import com.tip.dg4.toeic_exam.models.TestHistory;
import org.springframework.stereotype.Component;

@Component
public class TestHistoryMapper {
    public TestHistory convertSendAnswerDtoToModel(SendAnswerDto sendAnswerDto) {
        TestHistory testHistory = new TestHistory();

        testHistory.setUserId(sendAnswerDto.getUserId());

        return testHistory;
    }
}
