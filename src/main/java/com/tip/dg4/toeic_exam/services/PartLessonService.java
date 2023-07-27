package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.PartLessonWithoutContentsDto;

import java.util.List;
import java.util.UUID;

public interface PartLessonService {
    void createLessonWithoutContents(UUID practiceId,
                                     UUID practicePartId,
                                     PartLessonWithoutContentsDto partLessonWithoutContentsDto);
    List<PartLessonWithoutContentsDto> getLessonsWithoutContentsByPartId(UUID practicePartId);
}
