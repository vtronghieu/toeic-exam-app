package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.PracticeDto;
import com.tip.dg4.toeic_exam.dto.requests.PracticeReq;
import com.tip.dg4.toeic_exam.models.Lesson;
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.models.Part;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PracticeService {
    void createPractice(PracticeReq practiceReq);

    List<PracticeReq> getAllPractices();

    PracticeDto getPracticeById(UUID id);

    void updatePractice(UUID id, PracticeReq practiceReq);

    void deletePractice(UUID practiceId);

    boolean existsById(UUID id);

    Optional<Practice> findById(UUID id);

    List<Practice> findAll();

    Practice save(Practice practice);

    void saveByPart(Part part);

    void saveByLesson(Lesson lesson);
}
