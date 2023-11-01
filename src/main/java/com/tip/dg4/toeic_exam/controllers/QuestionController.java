package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.dto.requests.QuestionReq;
import com.tip.dg4.toeic_exam.services.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.QUESTION_API_ROOT)
@RequiredArgsConstructor
@Validated
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping(params = TExamParamConstant.OBJECT_TYPE_ID,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> createQuestion(@RequestParam(TExamParamConstant.OBJECT_TYPE_ID) UUID objectTypeId,
                                                       @RequestBody @Valid QuestionReq questionReq) {
        questionService.createQuestion(objectTypeId, questionReq);

        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {TExamParamConstant.PAGE, TExamParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getQuestions(@RequestParam(name = TExamParamConstant.PAGE,
                                                                   defaultValue = TExamParamConstant.PAGE_DEFAULT) int page,
                                                     @RequestParam(name = TExamParamConstant.SIZE,
                                                                   defaultValue = TExamParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S002,
                questionService.getQuestions(page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = TExamParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getQuestionById(@RequestParam(TExamParamConstant.ID) UUID id) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S003,
                questionService.getQuestionById(id)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {TExamParamConstant.TYPE, TExamParamConstant.PAGE, TExamParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getQuestionsByType(@RequestParam(TExamParamConstant.TYPE) String type,
                                                           @RequestParam(name = TExamParamConstant.PAGE,
                                                                         defaultValue = TExamParamConstant.PAGE_DEFAULT) int page,
                                                           @RequestParam(name = TExamParamConstant.SIZE,
                                                                         defaultValue = TExamParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S002,
                questionService.getQuestionsByType(type, page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {TExamParamConstant.OBJECT_TYPE_ID, TExamParamConstant.PAGE, TExamParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getQuestionsByObjectTypeId(@RequestParam(value = TExamParamConstant.OBJECT_TYPE_ID, required = false) UUID objectTypeId,
                                                                   @RequestParam(name = TExamParamConstant.PAGE,
                                                                                 defaultValue = TExamParamConstant.PAGE_DEFAULT) int page,
                                                                   @RequestParam(name = TExamParamConstant.SIZE,
                                                                                defaultValue = TExamParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S002,
                questionService.getQuestionsByObjectTypeId(objectTypeId, page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> updateQuestion(@RequestBody @Valid QuestionReq questionREQ) {
        questionService.updateQuestion(questionREQ);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = TExamParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> deleteQuestionById(@RequestParam(TExamParamConstant.ID) UUID id) {
        questionService.deleteQuestionById(id);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S005
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
