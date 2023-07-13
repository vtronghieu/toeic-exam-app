package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.VocabularyAnswerDto;
import com.tip.dg4.toeic_exam.dto.VocabularyQuestionDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface VocabularyQuestionController {
    ResponseEntity<ResponseData> getAllQuestions();
    ResponseEntity<ResponseData> getQuestionsByVocabularyIds(List<UUID> vocabularyIds);
    ResponseEntity<ResponseData> createQuestion(VocabularyQuestionDto vocabularyQuestionDto);
    ResponseEntity<ResponseData> updateQuestion();
    ResponseEntity<ResponseData> deleteQuestionById(UUID questionId);
    ResponseEntity<ResponseData> sendVocabularyAnswers(VocabularyAnswerDto vocabularyAnswerDto);
}
