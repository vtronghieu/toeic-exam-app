package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.PracticeDto;
import com.tip.dg4.toeic_exam.dto.requests.PracticeReq;
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.models.enums.PracticeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PracticeMapper {
    private final PartMapper partMapper;

    public Practice convertReqToModel(PracticeReq practiceReq) {
        return Practice.builder()
                .id(practiceReq.getId())
                .name(practiceReq.getName())
                .type(PracticeType.getType(practiceReq.getType()))
                .imageURL(practiceReq.getImageURL())
                .build();
    }

    public PracticeReq convertModelToReq(Practice practice) {
        return PracticeReq.builder()
                .id(practice.getId())
                .name(practice.getName())
                .type(PracticeType.getValueType(practice.getType()))
                .imageURL(practice.getImageURL())
                .build();
    }

    public PracticeDto convertModelToDto(Practice practice) {
        return PracticeDto.builder()
                .id(practice.getId())
                .name(practice.getName())
                .type(PracticeType.getValueType(practice.getType()))
                .imageURL(practice.getImageURL())
                .parts(partMapper.convertModelsToDTOs(practice.getParts()))
                .build();
    }
}
