package com.tip.dg4.toeic_exam.services;

<<<<<<< Updated upstream
import com.tip.dg4.toeic_exam.dto.PartLessonWithoutContentsDto;
=======
import com.tip.dg4.toeic_exam.dto.PartLessonDto;
>>>>>>> Stashed changes

import java.util.List;
import java.util.UUID;

public interface PartLessonService {
<<<<<<< Updated upstream
    void createLessonWithoutContents(UUID practiceId,
                                     UUID practicePartId,
                                     PartLessonWithoutContentsDto partLessonWithoutContentsDto);
    List<PartLessonWithoutContentsDto> getLessonsWithoutContentsByPartId(UUID practicePartId);
=======
    void createPartLesson(PartLessonDto partLessonDto);
    List<PartLessonDto> getPartLessonsByPartId(UUID practicePartId);
    void updatePartLesson(UUID partLessonId, PartLessonDto partLessonDto);
    void deletePartLesson(UUID partLessonId);
>>>>>>> Stashed changes
}
