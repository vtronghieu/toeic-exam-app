package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.dto.requests.QuestionReq;
import com.tip.dg4.toeic_exam.models.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {
    void createQuestion(QuestionReq questionReq);

    List<QuestionDto> getQuestions(int page, int size);

    QuestionDto getQuestionById(UUID id);

    List<QuestionDto> getQuestionsByType(String type, int page, int size);

    List<QuestionDto> getQuestionsByObjectTypeId(UUID objectTypeId, int page, int size);

    List<QuestionDto> getQuestionsByObjectTypeId(UUID objectTypeId);

    void updateQuestionById(UUID id, QuestionDto questionDto);

    void deleteQuestionById(UUID id);

    Optional<Question> findById(UUID questionId);

    List<Question> findByIDs(List<UUID> questionIDs);

    List<UUID> getQuestionIDsByQuestions(List<QuestionDto> questions);

    List<Question> findAll();

    Question save(Question question);
}
