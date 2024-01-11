package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.question.ListenAnswerDto;
import com.tip.dg4.toeic_exam.models.ListenAnswer;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ListenAnswerMapper {
    public ListenAnswer convertDtoToModel(ListenAnswerDto listenAnswerDto) {
        ListenAnswer listenAnswer = new ListenAnswer();

        if (Objects.nonNull(listenAnswerDto)) {
            listenAnswer.setAnswerContent(listenAnswerDto.getAnswerContent());
            listenAnswer.setCorrectPercent(listenAnswerDto.getCorrectPercent());
        }

        return listenAnswer;
    }

    public ListenAnswerDto convertModelToDto(ListenAnswer listenAnswer) {
        ListenAnswerDto listenAnswerDto = new ListenAnswerDto();

        if (Objects.nonNull(listenAnswer)) {
            listenAnswerDto.setAnswerContent(listenAnswer.getAnswerContent());
            listenAnswerDto.setCorrectPercent(listenAnswer.getCorrectPercent());
        }

        return listenAnswerDto;
    }
}
