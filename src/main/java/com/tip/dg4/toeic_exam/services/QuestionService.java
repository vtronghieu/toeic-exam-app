package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.QuestionDto;

import java.util.List;
import java.util.UUID;

public interface QuestionService {
    void createQuestion(QuestionDto questionDto);
    List<QuestionDto> getAllQuestions();
    QuestionDto getQuestionsByObjectTypeId(UUID objectTypeId);
    List<QuestionDto> getQuestionsByType(String type);
    List<QuestionDto> getQuestionsByObjectTypeIds(List<UUID> objectTypeIds);
    QuestionDto getQuestionById(UUID questionId);
    void updateQuestion(UUID questionId, QuestionDto questionDto);
    void deleteQuestionById(UUID questionId);
    boolean existsById(UUID questionId);
}
