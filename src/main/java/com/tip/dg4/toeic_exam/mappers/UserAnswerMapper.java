package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.ListenAnswerDto;
import com.tip.dg4.toeic_exam.dto.UserAnswerDto;
import com.tip.dg4.toeic_exam.models.ListenAnswer;
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
        ListenAnswer listenAnswer = listenAnswerMapper.convertDtoToModel(userAnswerDto.getListenAnswer());

        userAnswer.setQuestionId(userAnswerDto.getQuestionId());
//        userAnswer.setOptionAnswer(userAnswerDto.getOptionAnswer());
//        userAnswer.setListenAnswer(listenAnswer);
//        userAnswer.setWriteAnswer(userAnswerDto.getWriteAnswer());
//        userAnswer.setIsCorrect(userAnswerDto.getIsCorrect());

        return userAnswer;
    }

    public UserAnswerDto convertModelToDto(UserAnswer userAnswer) {
        UserAnswerDto userAnswerDto = new UserAnswerDto();
//        ListenAnswerDto listenAnswerDto = listenAnswerMapper.convertModelToDto(userAnswer.getListenAnswer());
//
//        userAnswerDto.setQuestionId(userAnswer.getQuestionId());
//        userAnswerDto.setOptionAnswer(userAnswer.getOptionAnswer());
//        userAnswerDto.setListenAnswer(listenAnswerDto);
//        userAnswerDto.setWriteAnswer(userAnswer.getWriteAnswer());
//        userAnswerDto.setIsCorrect(userAnswer.getIsCorrect());

        return userAnswerDto;
    }
}
