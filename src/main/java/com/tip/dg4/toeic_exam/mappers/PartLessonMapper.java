package com.tip.dg4.toeic_exam.mappers;

<<<<<<< Updated upstream
import com.tip.dg4.toeic_exam.dto.PartLessonWithoutContentsDto;
=======
import com.tip.dg4.toeic_exam.dto.PartLessonDto;
>>>>>>> Stashed changes
import com.tip.dg4.toeic_exam.models.PartLesson;
import org.springframework.stereotype.Component;

@Component
public class PartLessonMapper {
<<<<<<< Updated upstream
    public PartLesson convertDtoWithoutContentsToModel(PartLessonWithoutContentsDto partLessonWithoutContentsDto) {
        PartLesson partLesson = new PartLesson();

        partLesson.setName(partLessonWithoutContentsDto.getName());
=======
    public PartLesson convertDtoToModel(PartLessonDto partLessonDto) {
        PartLesson partLesson = new PartLesson();

        partLesson.setId(partLessonDto.getId());
        partLesson.setPracticePartId(partLessonDto.getPracticePartId());
        partLesson.setName(partLessonDto.getName());
>>>>>>> Stashed changes

        return partLesson;
    }

<<<<<<< Updated upstream
    public PartLessonWithoutContentsDto convertModelDtoWithoutContents(PartLesson partLesson) {
        PartLessonWithoutContentsDto partLessonWithoutContentsDto = new PartLessonWithoutContentsDto();

        partLessonWithoutContentsDto.setName(partLesson.getName());

        return partLessonWithoutContentsDto;
=======
    public PartLessonDto convertModelDto(PartLesson partLesson) {
        PartLessonDto partLessonDto = new PartLessonDto();

        partLessonDto.setId(partLesson.getId());
        partLessonDto.setPracticePartId(partLesson.getPracticePartId());
        partLessonDto.setName(partLesson.getName());

        return partLessonDto;
>>>>>>> Stashed changes
    }
}
