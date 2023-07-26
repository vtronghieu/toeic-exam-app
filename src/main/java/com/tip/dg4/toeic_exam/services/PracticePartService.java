package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.PracticePartWithoutLessonsAndTestsDto;
import com.tip.dg4.toeic_exam.models.PracticePart;

import java.util.List;
import java.util.UUID;

public interface PracticePartService {
    void createPartWithoutLessonsAndTests(UUID practiceId,
                                          PracticePartWithoutLessonsAndTestsDto partWithoutLessonsAndTestsDto);
    List<PracticePartWithoutLessonsAndTestsDto> getPartsWithoutLessonsAndTestsByPracticeId(UUID practiceId);
    void updatePartWithoutLessonsAndTests(UUID practiceId,
                                          UUID practicePartId,
                                          PracticePartWithoutLessonsAndTestsDto practicePartWithoutLessonsAndTestsDto);
    void deletePartById(UUID practiceId, UUID practicePartId);
    List<PracticePart> getAllPracticeParts();
}
