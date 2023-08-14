package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.PartLessonDto;

import java.util.List;
import java.util.UUID;

public interface PartLessonService {
    void createPartLesson(PartLessonDto partLessonDto);
    List<PartLessonDto> getPartLessonsByPartId(UUID practicePartId);
    void updatePartLesson(UUID partLessonId, PartLessonDto partLessonDto);
    void deletePartLesson(UUID partLessonId);
    PartLessonDto getPartLessonById(UUID lessonId);
}
