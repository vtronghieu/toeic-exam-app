package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.PartTestWithoutUserAnswerAndFinishTimeDto;

import java.util.List;
import java.util.UUID;

public interface PartTestService {
    void createTest(UUID practiceId,
                    UUID partId,
                    PartTestWithoutUserAnswerAndFinishTimeDto partTestWithoutUserAnswerAndFinishTimeDto);
    List<PartTestWithoutUserAnswerAndFinishTimeDto> getTestsByPartId(UUID partId);
}
