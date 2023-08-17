package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.QuestionRequestDto;
import com.tip.dg4.toeic_exam.dto.QuestionResponseDto;
import com.tip.dg4.toeic_exam.models.Question;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {
    void createQuestion(QuestionRequestDto questionRequestDto);
    List<QuestionResponseDto> getAllQuestions(HttpServletResponse response);
    List<QuestionResponseDto> getQuestionsByObjectTypeId(UUID objectTypeId);
    List<QuestionResponseDto> getQuestionsByType(String type);
    List<QuestionResponseDto> getQuestionsByObjectTypeIds(List<UUID> objectTypeIds);
    QuestionResponseDto getQuestionById(UUID questionId);
    void updateQuestion(UUID questionId, QuestionResponseDto questionResponseDto);
    void deleteQuestionById(UUID questionId);
    boolean existsById(UUID questionId);
    Optional<Question> findById(UUID questionId);
}
