package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.models.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {
    void createQuestion(QuestionDto questionDto);
    List<QuestionDto> getAllQuestions();
    List<QuestionDto> getQuestionsByObjectTypeId(UUID objectTypeId);
    List<QuestionDto> getQuestionsByType(String type);
    List<QuestionDto> getQuestionsByObjectTypeIds(List<UUID> objectTypeIds);
    void updateQuestion(UUID questionId, QuestionDto questionDto);
    void deleteQuestionById(UUID questionId);
    boolean existsById(UUID questionId);
    Optional<Question> findById(UUID questionId);
}
