package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.PartTestDto;
import com.tip.dg4.toeic_exam.models.PartTest;
import com.tip.dg4.toeic_exam.models.PracticeType;
import org.springframework.stereotype.Component;

@Component
public class PartTestMapper {
    public PartTest convertDtoToModel(PartTestDto partTestDto) {
        PartTest partTest = new PartTest();

        partTest.setId(partTestDto.getId());
        partTest.setPracticePartId(partTestDto.getPracticePartId());
        partTest.setType(PracticeType.getType(partTestDto.getType()));
        partTest.setName(partTestDto.getName());

        return partTest;
    }

    public PartTestDto convertModelToDto(PartTest partTest) {
        PartTestDto partTestDto = new PartTestDto();

        partTestDto.setId(partTest.getId());
        partTestDto.setPracticePartId(partTest.getPracticePartId());
        partTestDto.setType(PracticeType.getValueType(partTest.getType()));
        partTestDto.setName(partTest.getName());

        return partTestDto;
    }
}
