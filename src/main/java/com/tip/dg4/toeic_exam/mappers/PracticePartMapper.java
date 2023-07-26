package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.PracticePartWithoutLessonsAndTestsDto;
import com.tip.dg4.toeic_exam.models.PracticePart;
import org.springframework.stereotype.Component;

@Component
public class PracticePartMapper {
    public PracticePart convertDtoWithoutLessonsAndTestsToModel(PracticePartWithoutLessonsAndTestsDto partWithoutLessonsAndTestsDto) {
        PracticePart practicePart = new PracticePart();

        practicePart.setId(partWithoutLessonsAndTestsDto.getId());
        practicePart.setName(partWithoutLessonsAndTestsDto.getName());
        practicePart.setImage(partWithoutLessonsAndTestsDto.getImage());
        practicePart.setDescription(partWithoutLessonsAndTestsDto.getDescription());

        return practicePart;
    }

    public PracticePartWithoutLessonsAndTestsDto convertModelDtoWithoutLessonsAndTests(PracticePart practicePart) {
        PracticePartWithoutLessonsAndTestsDto practicePartWithoutLessonsAndTestsDto = new PracticePartWithoutLessonsAndTestsDto();

        practicePartWithoutLessonsAndTestsDto.setId(practicePart.getId());
        practicePartWithoutLessonsAndTestsDto.setName(practicePart.getName());
        practicePartWithoutLessonsAndTestsDto.setImage(practicePart.getImage());
        practicePartWithoutLessonsAndTestsDto.setDescription(practicePart.getDescription());

        return practicePartWithoutLessonsAndTestsDto;
    }
}
