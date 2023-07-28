package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.PracticeDto;

import java.util.List;
import java.util.UUID;

public interface PracticeService {
    void createPractice(PracticeDto practiceDto);
    List<PracticeDto> getAllPractices();
    void updatePractice(UUID practiceId, PracticeDto practiceDto);
    void deletePractice(UUID practiceId);
    boolean existsById(UUID practiceId);
}
