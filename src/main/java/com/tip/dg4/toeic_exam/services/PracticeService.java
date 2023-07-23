package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.PracticeWithoutPartsDto;

import java.util.List;
import java.util.UUID;

public interface PracticeService {
    void createPracticeWithoutParts(PracticeWithoutPartsDto practiceWithoutPartsDto);
    List<PracticeWithoutPartsDto> getAllPracticesWithoutParts();
    void updatePracticeWithoutParts(UUID practiceId, PracticeWithoutPartsDto practiceWithoutPartsDto);
    void deletePracticeWithoutParts(UUID practiceId);

}
