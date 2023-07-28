package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.QUESTION_API_ROOT)
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping(path = TExamApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> createQuestion(@RequestBody QuestionDto questionDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        questionService.createQuestion(questionDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getAllQuestions() {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S002,
                questionService.getAllQuestions()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.OBJECT_TYPE_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getQuestionsByObjectTypeId(@RequestParam(TExamParamConstant.OBJECT_TYPE_ID) UUID objectTypeId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S003,
                questionService.getQuestionsByObjectTypeId(objectTypeId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> updateQuestion(@RequestParam(TExamParamConstant.ID) UUID questionId,
                                                       @RequestBody QuestionDto questionDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        questionService.updateQuestion(questionId, questionDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(path = TExamApiConstant.API_EMPTY,
                   params = TExamParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> deleteQuestionById(@RequestParam(TExamParamConstant.ID) UUID questionId) {
        HttpStatus httpStatus = HttpStatus.OK;
        questionService.deleteQuestionById(questionId);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S005
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
