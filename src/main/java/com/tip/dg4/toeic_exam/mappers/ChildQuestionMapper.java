package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.ChildQuestionDto;
import com.tip.dg4.toeic_exam.models.ChildQuestion;
import org.springframework.stereotype.Component;

@Component
public class ChildQuestionMapper {
    public ChildQuestion convertDtoToModel(ChildQuestionDto childQuestionDto) {
        ChildQuestion childQuestion = new ChildQuestion();

        childQuestion.setId(childQuestionDto.getId());
        childQuestion.setQuestionId(childQuestionDto.getQuestionId());
        childQuestion.setTextQuestion(childQuestionDto.getTextQuestion());
        childQuestion.setAnswerA(childQuestionDto.getAnswerA());
        childQuestion.setAnswerB(childQuestionDto.getAnswerB());
        childQuestion.setAnswerC(childQuestionDto.getAnswerC());
        childQuestion.setAnswerD(childQuestionDto.getAnswerD());
        childQuestion.setCorrectAnswer(childQuestionDto.getCorrectAnswer());

        return childQuestion;
    }

    public ChildQuestionDto convertModelToDto(ChildQuestion childQuestion) {
        ChildQuestionDto childQuestionDto = new ChildQuestionDto();

        childQuestionDto.setId(childQuestion.getId());
        childQuestionDto.setQuestionId(childQuestion.getQuestionId());
        childQuestionDto.setTextQuestion(childQuestion.getTextQuestion());
        childQuestionDto.setAnswerA(childQuestion.getAnswerA());
        childQuestionDto.setAnswerB(childQuestion.getAnswerB());
        childQuestionDto.setAnswerC(childQuestion.getAnswerC());
        childQuestionDto.setAnswerD(childQuestion.getAnswerD());
        childQuestionDto.setCorrectAnswer(childQuestion.getCorrectAnswer());

        return childQuestionDto;
    }
}
