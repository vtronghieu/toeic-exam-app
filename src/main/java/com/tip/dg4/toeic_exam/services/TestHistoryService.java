package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.ReplyAnswerDto;
import com.tip.dg4.toeic_exam.dto.SendAnswerDto;
import com.tip.dg4.toeic_exam.models.TestHistory;

import java.util.List;
import java.util.UUID;

public interface TestHistoryService {
    ReplyAnswerDto sendAnswer(SendAnswerDto sendAnswerDto);
    List<TestHistory> getTestHistoryOfTestIdByStatus(UUID userId, UUID testId);

}
