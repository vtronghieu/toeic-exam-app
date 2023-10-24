package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.LessonDto;
import com.tip.dg4.toeic_exam.dto.requests.LessonReq;
import com.tip.dg4.toeic_exam.models.Lesson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    void createLesson(LessonReq lessonREQ);

    List<LessonDto> getLessons();

    List<LessonDto> getLessonsByPartId(UUID partId);

    LessonDto getLessonById(UUID id);

    void updateLessonById(LessonReq lessonREQ);

    void deleteLessonById(UUID id);

    List<Lesson> findAll();

    Optional<Lesson> findById(UUID id);
}
