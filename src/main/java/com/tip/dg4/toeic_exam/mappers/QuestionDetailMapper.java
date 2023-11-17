package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.QuestionDetailDto;
import com.tip.dg4.toeic_exam.dto.requests.QuestionDetailReq;
import com.tip.dg4.toeic_exam.models.QuestionDetail;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class QuestionDetailMapper {
    public QuestionDetail convertDtoToModel(QuestionDetailDto questionDetailDto) {
        return QuestionDetail.builder()
                .id(questionDetailDto.getId())
                .questionId(questionDetailDto.getQuestionId())
                .contentQuestion(questionDetailDto.getContentQuestion())
                .answers(questionDetailDto.getAnswers())
                .correctAnswer(questionDetailDto.getCorrectAnswer())
                .build();
    }

    public QuestionDetailDto convertModelToDto(QuestionDetail questionDetail) {
        return QuestionDetailDto.builder()
                .id(questionDetail.getId())
                .questionId(questionDetail.getQuestionId())
                .contentQuestion(questionDetail.getContentQuestion())
                .answers(questionDetail.getAnswers())
                .correctAnswer(questionDetail.getCorrectAnswer())
                .build();
    }

    public List<QuestionDetail> convertDTOsToModels(List<QuestionDetailDto> questionDetailDTOs) {
        return Optional.ofNullable(questionDetailDTOs).orElse(new ArrayList<>())
                .stream().map(this::convertDtoToModel).toList();
    }

    public List<QuestionDetailDto> convertModelsToDTOs(List<QuestionDetail> questionDetails) {
        return Optional.ofNullable(questionDetails).orElse(new ArrayList<>())
                .stream().map(this::convertModelToDto).toList();
    }

    public QuestionDetail convertReqToModel(QuestionDetailReq questionDetailReq) {
        return QuestionDetail.builder()
                .id(questionDetailReq.getId())
                .contentQuestion(questionDetailReq.getContentQuestion())
                .answers(questionDetailReq.getAnswers())
                .correctAnswer(questionDetailReq.getCorrectAnswer())
                .build();
    }

    public List<QuestionDetail> convertREQsToModels(List<QuestionDetailReq> questionDetailREQs) {
        return Optional.ofNullable(questionDetailREQs).orElse(new ArrayList<>())
                .stream().map(this::convertReqToModel).toList();
    }
}
