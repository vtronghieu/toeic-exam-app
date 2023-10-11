package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.QuestionDetailDto;
import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.dto.requests.QuestionReq;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.QuestionDetail;
import com.tip.dg4.toeic_exam.models.enums.QuestionLevel;
import com.tip.dg4.toeic_exam.models.enums.QuestionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuestionMapper {
    private final QuestionDetailMapper questionDetailMapper;

    public Question convertDtoToModel(QuestionDto questionDto) {
        List<QuestionDetail> questionDetails = Optional.ofNullable(questionDto.getQuestionDetails())
                .orElse(new ArrayList<>()).stream()
                .map(questionDetailMapper::convertDtoToModel).toList();
        List<String> imageURLs = Optional.ofNullable(questionDto.getImageURLs()).orElse(new ArrayList<>());

        return Question.builder()
                .id(questionDto.getId())
                .type(QuestionType.getType(questionDto.getType()))
                .objectTypeId(questionDto.getObjectTypeId())
                .level(QuestionLevel.getLevel(questionDto.getLevel()))
                .imageURLs(imageURLs)
                .audioURL(questionDto.getAudioURL())
                .transcript(questionDto.getTranscript())
                .questionDetails(questionDetails)
                .build();
    }

    public QuestionDto convertModelToDto(Question question) {
        List<QuestionDetailDto> questionDetails = Optional.ofNullable(question.getQuestionDetails())
                .orElse(new ArrayList<>()).stream()
                .map(questionDetailMapper::convertModelToDto).toList();
        List<String> imageURLs = Optional.ofNullable(question.getImageURLs()).orElse(new ArrayList<>());

        return QuestionDto.builder()
                .id(question.getId())
                .type(QuestionType.getValueType(question.getType()))
                .objectTypeId(question.getObjectTypeId())
                .level(QuestionLevel.getValueLevel(question.getLevel()))
                .imageURLs(imageURLs)
                .audioURL(question.getAudioURL())
                .transcript(question.getTranscript())
                .questionDetails(questionDetails)

                .build();
    }

    public Question convertReqToModel(QuestionReq questionReq) {
        List<QuestionDetail> questionDetails = questionDetailMapper.convertREQsToModels(questionReq.getQuestionDetails());
        List<String> imageURLs = Optional.ofNullable(questionReq.getImageURLs()).orElse(new ArrayList<>());

        return Question.builder()
                .type(QuestionType.getType(questionReq.getType()))
                .objectTypeId(questionReq.getObjectTypeId())
                .level(QuestionLevel.getLevel(questionReq.getLevel()))
                .imageURLs(imageURLs)
                .audioURL(questionReq.getAudioURL())
                .transcript(questionReq.getTranscript())
                .questionDetails(questionDetails)
                .build();
    }
}
