package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.PartTestWithoutUserAnswerAndFinishTimeDto;
import com.tip.dg4.toeic_exam.models.PartTest;
import com.tip.dg4.toeic_exam.models.PracticeType;
import org.springframework.stereotype.Component;

@Component
public class PartTestMapper {
    public PartTest convertDtoWithoutUserAnswerAndFinishTimeToModel(PartTestWithoutUserAnswerAndFinishTimeDto testWithoutUserAnswerDto) {
        PartTest partTest = new PartTest();

        partTest.setName(testWithoutUserAnswerDto.getName());
        partTest.setType(PracticeType.getType(testWithoutUserAnswerDto.getType()));

        return partTest;
    }

    public PartTestWithoutUserAnswerAndFinishTimeDto convertModelToDtoWithoutUserAnswerAndFinishTime(PartTest partTest) {
        PartTestWithoutUserAnswerAndFinishTimeDto testWithoutUserAnswerDto = new PartTestWithoutUserAnswerAndFinishTimeDto();

        testWithoutUserAnswerDto.setName(partTest.getName());
        testWithoutUserAnswerDto.setType(PracticeType.getValueType(partTest.getType()));

        return testWithoutUserAnswerDto;
    }
}
