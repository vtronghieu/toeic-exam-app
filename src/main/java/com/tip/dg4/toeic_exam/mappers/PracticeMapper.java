package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.PracticeWithoutPartsDto;
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.models.PracticeType;
import org.springframework.stereotype.Component;

@Component
public class PracticeMapper {
    public Practice convertDtoWithoutPartsToModel(PracticeWithoutPartsDto practiceWithoutPartsDto) {
        Practice practice = new Practice();

        practice.setId(practiceWithoutPartsDto.getId());
        practice.setName(practiceWithoutPartsDto.getName());
        practice.setType(PracticeType.getType(practiceWithoutPartsDto.getType()));
        practice.setImage(practiceWithoutPartsDto.getImage());

        return practice;
    }

    public PracticeWithoutPartsDto convertModelToDtoWithoutParts(Practice practice) {
        PracticeWithoutPartsDto practiceWithoutPartsDto = new PracticeWithoutPartsDto();

        practiceWithoutPartsDto.setId(practice.getId());
        practiceWithoutPartsDto.setName(practice.getName());
        practiceWithoutPartsDto.setType(PracticeType.getValueType(practice.getType()));
        practiceWithoutPartsDto.setImage(practice.getImage());

        return practiceWithoutPartsDto;
    }
}
