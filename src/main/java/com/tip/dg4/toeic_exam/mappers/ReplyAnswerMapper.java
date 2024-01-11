package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.question.ReplyAnswerDto;
import com.tip.dg4.toeic_exam.dto.question.SendAnswerDto;
import com.tip.dg4.toeic_exam.models.QuestionDetail;
import org.springframework.stereotype.Component;

@Component
public class ReplyAnswerMapper {
    public ReplyAnswerDto convertSendAnswerDtoToDto(SendAnswerDto sendAnswerDto, QuestionDetail questionDetail) {
        ReplyAnswerDto replyAnswerDto = new ReplyAnswerDto();

        replyAnswerDto.setQuestionId(sendAnswerDto.getQuestionId());
        replyAnswerDto.setChildQuestionId(sendAnswerDto.getChildQuestionId());
        replyAnswerDto.setUserAnswer(sendAnswerDto.getUserAnswer());
        replyAnswerDto.setCorrectAnswer(questionDetail.getCorrectAnswer());
        replyAnswerDto.setCorrect(sendAnswerDto.getUserAnswer().equals(questionDetail.getCorrectAnswer()));

        return replyAnswerDto;
    }
}
