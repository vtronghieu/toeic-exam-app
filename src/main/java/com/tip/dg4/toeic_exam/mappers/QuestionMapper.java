package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.ChildQuestionDto;
import com.tip.dg4.toeic_exam.dto.QuestionRequestDto;
import com.tip.dg4.toeic_exam.dto.QuestionResponseDto;
import com.tip.dg4.toeic_exam.models.ChildQuestion;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.QuestionLevel;
import com.tip.dg4.toeic_exam.models.QuestionType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class QuestionMapper {
    private final ChildQuestionMapper childQuestionMapper;

    public QuestionMapper(ChildQuestionMapper childQuestionMapper) {
        this.childQuestionMapper = childQuestionMapper;
    }

    public Question convertRequestDtoToModel(QuestionRequestDto questionRequestDto) {
        Question question = new Question();
        List<String> imageUrls = questionRequestDto.getImages().stream().map(MultipartFile::getOriginalFilename).toList();

        question.setId(questionRequestDto.getId());
        question.setType(QuestionType.getType(questionRequestDto.getType()));
        question.setObjectTypeId(questionRequestDto.getObjectTypeId());
        question.setLevel(QuestionLevel.getLevel(questionRequestDto.getLevel()));
        question.setTranscript(questionRequestDto.getTranscript());
        question.setAudioQuestion(questionRequestDto.getAudioQuestion());
        question.setImageUrls(imageUrls);

        return question;
    }

    public Question convertDtoToModel(QuestionResponseDto questionResponseDto) {
        Question question = new Question();

        question.setId(questionResponseDto.getId());
        question.setType(QuestionType.getType(questionResponseDto.getType()));
        question.setObjectTypeId(questionResponseDto.getObjectTypeId());
        question.setLevel(QuestionLevel.getLevel(questionResponseDto.getLevel()));
        question.setTranscript(questionResponseDto.getTranscript());
        question.setAudioQuestion(questionResponseDto.getAudioQuestion());
        question.setImageUrls(questionResponseDto.getImageUrls());

        return question;
    }

    public QuestionResponseDto convertModelToDto(Question question, List<ChildQuestion> childQuestions) {
        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        List<ChildQuestionDto> questionDTOs = childQuestions.stream().map(childQuestionMapper::convertModelToDto).toList();

        questionResponseDto.setId(question.getId());
        questionResponseDto.setType(QuestionType.getValueType(question.getType()));
        questionResponseDto.setObjectTypeId(question.getObjectTypeId());
        questionResponseDto.setLevel(QuestionLevel.getValueLevel(question.getLevel()));
        questionResponseDto.setAudioQuestion(question.getAudioQuestion());
        questionResponseDto.setTranscript(question.getTranscript());
        questionResponseDto.setImageUrls(question.getImageUrls());
        questionResponseDto.setQuestions(questionDTOs);

        return questionResponseDto;
    }
}
