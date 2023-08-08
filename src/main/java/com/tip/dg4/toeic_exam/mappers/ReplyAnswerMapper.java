package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.ReplyAnswerDto;
import com.tip.dg4.toeic_exam.dto.SendAnswerDto;
import com.tip.dg4.toeic_exam.models.ChildQuestion;
import org.springframework.stereotype.Component;

@Component
public class ReplyAnswerMapper {
    public ReplyAnswerDto convertSendAnswerDtoToDto(SendAnswerDto sendAnswerDto, ChildQuestion childQuestion) {
        ReplyAnswerDto replyAnswerDto = new ReplyAnswerDto();

        replyAnswerDto.setQuestionId(sendAnswerDto.getQuestionId());
        replyAnswerDto.setChildQuestionId(sendAnswerDto.getChildQuestionId());
        replyAnswerDto.setUserAnswer(sendAnswerDto.getUserAnswer());
        replyAnswerDto.setCorrectAnswer(childQuestion.getCorrectAnswer());
        replyAnswerDto.setCorrect(sendAnswerDto.getUserAnswer().equals(childQuestion.getCorrectAnswer()));

        return replyAnswerDto;
    }
}
