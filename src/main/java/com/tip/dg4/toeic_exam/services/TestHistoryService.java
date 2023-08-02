package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.ReplyAnswerDto;
import com.tip.dg4.toeic_exam.dto.SendAnswerDto;

public interface TestHistoryService {
    ReplyAnswerDto sendAnswer(SendAnswerDto sendAnswerDto);
}
