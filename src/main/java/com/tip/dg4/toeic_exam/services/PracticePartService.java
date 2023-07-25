package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.PracticePartWithoutLessonsAndTestsDto;

import java.util.List;
import java.util.UUID;

public interface PracticePartService {
    void createPartWithoutLessonsAndTests(UUID practiceId,
                                          PracticePartWithoutLessonsAndTestsDto partWithoutLessonsAndTestsDto);
    List<PracticePartWithoutLessonsAndTestsDto> getPartsWithoutLessonsAndTestsByPracticeId(UUID practiceId);
}
