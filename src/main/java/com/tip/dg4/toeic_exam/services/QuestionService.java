package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.dto.requests.QuestionReq;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {
    Question createQuestion(UUID objectTypeId, QuestionReq questionReq);

    List<QuestionDto> getQuestions(int page, int size);

    QuestionDto getQuestionById(UUID id);

    List<QuestionDto> getQuestionsByType(String type, int page, int size);

    List<QuestionDto> getQuestionsByObjectTypeId(UUID objectTypeId, int page, int size);

    List<QuestionDto> getQuestionsByObjectTypeId(UUID objectTypeId);

    Question updateQuestion(QuestionReq questionReq);

    List<Question> updateQuestions(List<QuestionReq> questionREQs);

    void deleteQuestionById(UUID id);

    List<Question> createQuestions(Test test, List<QuestionReq> questionREQs);

    Optional<Question> findById(UUID questionId);

    List<Question> findByIDs(List<UUID> questionIDs);

    List<UUID> getQuestionIDsByQuestionDTOs(List<QuestionDto> questionDTOs);

    List<UUID> getQuestionIDsByQuestions(List<Question> questions);

    List<Question> findAll();

    Question save(Question question);
}
