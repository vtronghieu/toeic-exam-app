package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.QuestionDetailDto;
import com.tip.dg4.toeic_exam.services.QuestionDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.QUESTION_DETAIL_API_ROOT)
@RequiredArgsConstructor
@Validated
public class QuestionDetailController {
    private final QuestionDetailService questionDetailService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> createQuestionDetails(@RequestBody @Valid List<QuestionDetailDto> questionDetailDTOs) {
        questionDetailService.createQuestionDetails(questionDetailDTOs);

        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_DETAIL_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {TExamParamConstant.PAGE, TExamParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getQuestionDetails(@RequestParam(name = TExamParamConstant.PAGE,
                                                                         defaultValue = TExamParamConstant.PAGE_DEFAULT) int page,
                                                           @RequestParam(name = TExamParamConstant.SIZE,
                                                                         defaultValue = TExamParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_DETAIL_S004,
                questionDetailService.getQuestionDetails(page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = TExamParamConstant.QUESTION_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getQuestionDetailsByQuestionId(@RequestParam(TExamParamConstant.QUESTION_ID) UUID questionId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_DETAIL_S002,
                questionDetailService.getQuestionDetailsByQuestionId(questionId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = TExamParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getQuestionDetailById(@RequestParam(TExamParamConstant.ID) UUID id) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_DETAIL_S003,
                questionDetailService.getQuestionDetailById(id)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> updateQuestionDetail(@RequestBody @Valid QuestionDetailDto questionDetailDto) {
        questionDetailService.updateQuestionDetail(questionDetailDto);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_DETAIL_S005
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = TExamParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> deleteQuestionDetailById(@RequestParam UUID id) {
        questionDetailService.deleteQuestionDetailById(id);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_DETAIL_S006
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}