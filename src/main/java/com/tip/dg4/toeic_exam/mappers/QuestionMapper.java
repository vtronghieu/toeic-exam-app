package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.ChildQuestionDto;
import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.models.ChildQuestion;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.QuestionLevel;
import com.tip.dg4.toeic_exam.models.QuestionType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionMapper {
    private final ChildQuestionMapper childQuestionMapper;

    public QuestionMapper(ChildQuestionMapper childQuestionMapper) {
        this.childQuestionMapper = childQuestionMapper;
    }

    public Question convertDtoToModel(QuestionDto questionDto) {
        Question question = new Question();

        question.setId(questionDto.getId());
        question.setType(QuestionType.getType(questionDto.getType()));
        question.setObjectTypeId(questionDto.getObjectTypeId());
        question.setLevel(QuestionLevel.getLevel(questionDto.getLevel()));
        question.setAudioQuestion(questionDto.getAudioQuestion());
        question.setImages(questionDto.getImages());

        return question;
    }

    public QuestionDto convertModelToDto(Question question, List<ChildQuestion> childQuestions) {
        QuestionDto questionDto = new QuestionDto();
        List<ChildQuestionDto> questionDTOs = childQuestions.stream().map(childQuestionMapper::convertModelToDto).toList();

        questionDto.setId(question.getId());
        questionDto.setType(QuestionType.getValueType(question.getType()));
        questionDto.setObjectTypeId(question.getObjectTypeId());
        questionDto.setLevel(QuestionLevel.getValueLevel(question.getLevel()));
        questionDto.setAudioQuestion(question.getAudioQuestion());
        questionDto.setImages(question.getImages());
        questionDto.setQuestions(questionDTOs);

        return questionDto;
    }
}
