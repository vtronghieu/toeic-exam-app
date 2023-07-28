package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.PracticeDto;
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.models.PracticeType;
import org.springframework.stereotype.Component;

@Component
public class PracticeMapper {
    public Practice convertDtoToModel(PracticeDto practiceDto) {
        Practice practice = new Practice();

        practice.setId(practiceDto.getId());
        practice.setName(practiceDto.getName());
        practice.setType(PracticeType.getType(practiceDto.getType()));
        practice.setImage(practiceDto.getImage());

        return practice;
    }

    public PracticeDto convertModelToDto(Practice practice) {
        PracticeDto practiceDto = new PracticeDto();

        practiceDto.setId(practice.getId());
        practiceDto.setName(practice.getName());
        practiceDto.setType(PracticeType.getValueType(practice.getType()));
        practiceDto.setImage(practice.getImage());

        return practiceDto;
    }
}
