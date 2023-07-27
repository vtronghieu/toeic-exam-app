package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.PartLessonWithoutContentsDto;
import com.tip.dg4.toeic_exam.models.PartLesson;
import org.springframework.stereotype.Component;

@Component
public class PartLessonMapper {
    public PartLesson convertDtoWithoutContentsToModel(PartLessonWithoutContentsDto partLessonWithoutContentsDto) {
        PartLesson partLesson = new PartLesson();

        partLesson.setName(partLessonWithoutContentsDto.getName());

        return partLesson;
    }

    public PartLessonWithoutContentsDto convertModelDtoWithoutContents(PartLesson partLesson) {
        PartLessonWithoutContentsDto partLessonWithoutContentsDto = new PartLessonWithoutContentsDto();

        partLessonWithoutContentsDto.setName(partLesson.getName());

        return partLessonWithoutContentsDto;
    }
}
