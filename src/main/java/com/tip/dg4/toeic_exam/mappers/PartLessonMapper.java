package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.PartLessonDto;
import com.tip.dg4.toeic_exam.models.PartLesson;
import org.springframework.stereotype.Component;

@Component
public class PartLessonMapper {
    public PartLesson convertDtoToModel(PartLessonDto partLessonDto) {
        PartLesson partLesson = new PartLesson();

        partLesson.setId(partLessonDto.getId());
        partLesson.setPracticePartId(partLessonDto.getPracticePartId());
        partLesson.setName(partLessonDto.getName());

        return partLesson;
    }

    public PartLessonDto convertModelDto(PartLesson partLesson) {
        PartLessonDto partLessonDto = new PartLessonDto();

        partLessonDto.setId(partLesson.getId());
        partLessonDto.setPracticePartId(partLesson.getPracticePartId());
        partLessonDto.setName(partLesson.getName());

        return partLessonDto;
    }
}
