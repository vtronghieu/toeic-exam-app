package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.ParamConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
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
@RequestMapping(path = ApiConstant.QUESTION_API_ROOT)
@RequiredArgsConstructor
@Validated
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping(params = ParamConstant.OBJECT_TYPE_ID,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> createQuestion(@RequestParam(ParamConstant.OBJECT_TYPE_ID) UUID objectTypeId,
                                                       @RequestBody @Valid QuestionReq questionReq) {
        questionService.createQuestion(objectTypeId, questionReq);

        HttpStatus httpStatus = HttpStatus.CREATED;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {ParamConstant.PAGE, ParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getQuestions(@RequestParam(name = ParamConstant.PAGE,
                                                                   defaultValue = ParamConstant.PAGE_DEFAULT) int page,
                                                     @RequestParam(name = ParamConstant.SIZE,
                                                                   defaultValue = ParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_S002,
                questionService.getQuestions(page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getQuestionById(@RequestParam(ParamConstant.ID) UUID id) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_S003,
                questionService.getQuestionById(id)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {ParamConstant.TYPE, ParamConstant.PAGE, ParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getQuestionsByType(@RequestParam(ParamConstant.TYPE) String type,
                                                           @RequestParam(name = ParamConstant.PAGE,
                                                                         defaultValue = ParamConstant.PAGE_DEFAULT) int page,
                                                           @RequestParam(name = ParamConstant.SIZE,
                                                                         defaultValue = ParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_S002,
                questionService.getQuestionsByType(type, page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {ParamConstant.OBJECT_TYPE_ID, ParamConstant.PAGE, ParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getQuestionsByObjectTypeId(@RequestParam(value = ParamConstant.OBJECT_TYPE_ID, required = false) UUID objectTypeId,
                                                                   @RequestParam(name = ParamConstant.PAGE,
                                                                                 defaultValue = ParamConstant.PAGE_DEFAULT) int page,
                                                                   @RequestParam(name = ParamConstant.SIZE,
                                                                                defaultValue = ParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_S002,
                questionService.getQuestionsByObjectTypeId(objectTypeId, page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> updateQuestion(@RequestBody @Valid QuestionReq questionREQ) {
        questionService.updateQuestion(questionREQ);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = ParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> deleteQuestionById(@RequestParam(ParamConstant.ID) UUID id) {
        questionService.deleteQuestionById(id);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_S005
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
