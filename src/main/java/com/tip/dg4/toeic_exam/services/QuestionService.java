package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.QuestionType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {
    void createQuestion(QuestionDto questionDto);
    List<QuestionDto> getAllQuestions();
    List<QuestionDto> getQuestionsByObjectTypeId(UUID objectTypeId);
    Optional<Question> findByTypeAndId(QuestionType questionType, UUID questionId);
    void updateQuestion(UUID questionId, QuestionDto questionDto);
    void deleteQuestionById(UUID questionId);
}
