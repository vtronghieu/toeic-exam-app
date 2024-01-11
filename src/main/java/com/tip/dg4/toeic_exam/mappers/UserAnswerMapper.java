package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.question.UserAnswerDto;
import com.tip.dg4.toeic_exam.models.UserAnswer;
import org.springframework.stereotype.Component;

@Component
public class UserAnswerMapper {
    private final ListenAnswerMapper listenAnswerMapper;

    public UserAnswerMapper(ListenAnswerMapper listenAnswerMapper) {
        this.listenAnswerMapper = listenAnswerMapper;
    }

    public UserAnswer convertDtoToModel(UserAnswerDto userAnswerDto) {
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setQuestionId(userAnswerDto.getQuestionId());

        return userAnswer;
    }

    public UserAnswerDto convertModelToDto(UserAnswer userAnswer) {
        UserAnswerDto userAnswerDto = new UserAnswerDto();
        return userAnswerDto;
    }
}
