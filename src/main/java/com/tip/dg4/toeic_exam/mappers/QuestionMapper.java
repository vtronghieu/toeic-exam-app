package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.QuestionLevel;
import com.tip.dg4.toeic_exam.models.QuestionType;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {
    public Question convertDtoToModel(QuestionDto questionDto) {
        Question question = new Question();

        question.setId(questionDto.getId());
        question.setType(QuestionType.getType(questionDto.getType()));
        question.setObjectTypeId(questionDto.getObjectTypeId());
        question.setLevel(QuestionLevel.getLevel(questionDto.getLevel()));
        question.setTextQuestion(questionDto.getTextQuestion());
        question.setAudioQuestion(questionDto.getAudioQuestion());
        question.setImages(questionDto.getImages());
        question.setOptionAnswers(questionDto.getOptionAnswers());

        return question;
    }

    public QuestionDto convertModelToDto(Question question) {
        QuestionDto questionDto = new QuestionDto();

        questionDto.setId(question.getId());
        questionDto.setType(QuestionType.getValueType(question.getType()));
        questionDto.setObjectTypeId(question.getObjectTypeId());
        questionDto.setLevel(QuestionLevel.getValueLevel(question.getLevel()));
        questionDto.setTextQuestion(question.getTextQuestion());
        questionDto.setAudioQuestion(question.getAudioQuestion());
        questionDto.setImages(question.getImages());
        questionDto.setOptionAnswers(question.getOptionAnswers());

        return questionDto;
    }
}
