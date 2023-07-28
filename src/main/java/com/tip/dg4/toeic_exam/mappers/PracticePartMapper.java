package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.PracticePartDto;
import com.tip.dg4.toeic_exam.models.PracticePart;
import org.springframework.stereotype.Component;

@Component
public class PracticePartMapper {
    public PracticePart convertDtoToModel(PracticePartDto practicePartDto) {
        PracticePart practicePart = new PracticePart();

        practicePart.setId(practicePartDto.getId());
        practicePart.setPracticeId(practicePartDto.getPracticeId());
        practicePart.setName(practicePartDto.getName());
        practicePart.setImage(practicePartDto.getImage());
        practicePart.setDescription(practicePartDto.getDescription());

        return practicePart;
    }

    public PracticePartDto convertModelToDto(PracticePart practicePart) {
        PracticePartDto practicePartDto = new PracticePartDto();

        practicePartDto.setId(practicePart.getId());
        practicePartDto.setPracticeId(practicePart.getPracticeId());
        practicePartDto.setName(practicePart.getName());
        practicePartDto.setImage(practicePart.getImage());
        practicePartDto.setDescription(practicePart.getDescription());

        return practicePartDto;
    }
}
