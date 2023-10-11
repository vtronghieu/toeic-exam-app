package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.QuestionDetailDto;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.QuestionDetail;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionDetailService {
    /*
    void deleteChildQuestionsByQuestionId(UUID questionId);
    */

    void createQuestionDetails(List<QuestionDetailDto> questionDetailDTOs);

    List<QuestionDetailDto> getQuestionDetails(int page, int size);

    List<QuestionDetailDto> getQuestionDetailsByQuestionId(UUID questionId);

    QuestionDetailDto getQuestionDetailById(UUID id);

    void updateQuestionDetail(QuestionDetailDto questionDetailDto);

    void deleteQuestionDetailById(UUID id);

    List<QuestionDetail> resolveQuestionDetailsForQuestion(Question question);

    Optional<QuestionDetail> findById(UUID id);

    boolean existsById(UUID id);

    List<QuestionDetail> findAll();

    List<QuestionDetail> findByQuestionId(UUID questionId);
}
