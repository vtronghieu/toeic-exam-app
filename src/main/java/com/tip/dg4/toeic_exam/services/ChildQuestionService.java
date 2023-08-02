package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.ChildQuestionDto;
import com.tip.dg4.toeic_exam.models.ChildQuestion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChildQuestionService {
    void createChildQuestions(UUID questionId, List<ChildQuestionDto> childQuestionDTOs);
    List<ChildQuestion> getChildQuestionsByQuestionId(UUID questionId);
    void updateChildQuestion(UUID questionId, List<ChildQuestionDto> childQuestionDTOs);
    void deleteChildQuestionsByQuestionId(UUID questionId);
    Optional<ChildQuestion> findById(UUID childQuestionId);
}
