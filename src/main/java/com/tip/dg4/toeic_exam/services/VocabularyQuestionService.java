package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.VocabularyAnswerDto;
import com.tip.dg4.toeic_exam.dto.VocabularyQuestionDto;
import com.tip.dg4.toeic_exam.dto.VocabularyTestHistoryDto;
import com.tip.dg4.toeic_exam.models.VocabularyTestHistory;

import java.util.List;
import java.util.UUID;

public interface VocabularyQuestionService {
    List<VocabularyQuestionDto> getAllQuestions();
    List<VocabularyQuestionDto> getQuestionsByVocabularyIds(List<UUID> questionId);
    void createQuestion(VocabularyQuestionDto vocabularyQuestionDto);
    void updateQuestion();
    void deleteQuestionById(UUID questionId);
    VocabularyTestHistoryDto sendVocabularyAnswers(VocabularyAnswerDto vocabularyAnswerDto);
}
