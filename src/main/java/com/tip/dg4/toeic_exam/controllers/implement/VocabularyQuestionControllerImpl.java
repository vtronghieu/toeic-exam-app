package com.tip.dg4.toeic_exam.controllers.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.controllers.VocabularyQuestionController;
import com.tip.dg4.toeic_exam.dto.VocabularyQuestionDto;
import com.tip.dg4.toeic_exam.services.VocabularyQuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.VOCABULARY_QUESTION_API_ROOT)
public class VocabularyQuestionControllerImpl implements VocabularyQuestionController {
    private final VocabularyQuestionService vocabularyQuestionService;

    public VocabularyQuestionControllerImpl(VocabularyQuestionService vocabularyQuestionService) {
        this.vocabularyQuestionService = vocabularyQuestionService;
    }

    @Override
    @GetMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getAllQuestions() {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_CATEGORY_S001,
                vocabularyQuestionService.getAllQuestions()
        );
        return new ResponseEntity<>(result, httpStatus);
    }

    @Override
    @GetMapping(path = TExamApiConstant.VOCABULARY_QUESTION_API_GET_BY_VOCABULARY_IDS,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getQuestionsByVocabularyIds(@RequestBody List<UUID> vocabularyIds) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_QUESTION_S005,
                vocabularyQuestionService.getQuestionsByVocabularyIds(vocabularyIds)
        );
        return new ResponseEntity<>(result, httpStatus);
    }

    @Override
    @PostMapping(path = TExamApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> createQuestion(@RequestBody VocabularyQuestionDto vocabularyQuestionDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        vocabularyQuestionService.createQuestion(vocabularyQuestionDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_QUESTION_S002
        );
        return new ResponseEntity<>(result, httpStatus);
    }

    @Override
    @PutMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> updateQuestion() {
        return null;
    }

    @Override
    @DeleteMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                   params = "questionId",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> deleteQuestionById(UUID questionId) {
        return null;
    }
}
