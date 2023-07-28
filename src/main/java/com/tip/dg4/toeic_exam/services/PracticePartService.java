package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.PracticePartDto;

import java.util.List;
import java.util.UUID;

public interface PracticePartService {
    void createPracticePart(PracticePartDto practicePartDto);
    List<PracticePartDto> getPracticePartsByPracticeId(UUID practiceId);
    void updatePracticePart(UUID practicePartId, PracticePartDto practicePartDto);
    void deletePracticePartById(UUID practicePartId);
    boolean existsById(UUID practicePartId);
}
