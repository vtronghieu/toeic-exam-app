package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.ParamConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
import com.tip.dg4.toeic_exam.dto.question.QuestionDetailDto;
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
@RequestMapping(path = ApiConstant.QUESTION_DETAIL_API_ROOT)
@RequiredArgsConstructor
@Validated
public class QuestionDetailController {
    private final QuestionDetailService questionDetailService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> createQuestionDetails(@RequestBody @Valid List<QuestionDetailDto> questionDetailDTOs) {
        questionDetailService.createQuestionDetails(questionDetailDTOs);

        HttpStatus httpStatus = HttpStatus.CREATED;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_DETAIL_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {ParamConstant.PAGE, ParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getQuestionDetails(@RequestParam(name = ParamConstant.PAGE,
                                                                         defaultValue = ParamConstant.PAGE_DEFAULT) int page,
                                                           @RequestParam(name = ParamConstant.SIZE,
                                                                         defaultValue = ParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_DETAIL_S004,
                questionDetailService.getQuestionDetails(page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.QUESTION_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getQuestionDetailsByQuestionId(@RequestParam(ParamConstant.QUESTION_ID) UUID questionId) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_DETAIL_S002,
                questionDetailService.getQuestionDetailsByQuestionId(questionId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getQuestionDetailById(@RequestParam(ParamConstant.ID) UUID id) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_DETAIL_S003,
                questionDetailService.getQuestionDetailById(id)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> updateQuestionDetail(@RequestBody @Valid QuestionDetailDto questionDetailDto) {
        questionDetailService.updateQuestionDetail(questionDetailDto);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_DETAIL_S005
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = ParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> deleteQuestionDetailById(@RequestParam UUID id) {
        questionDetailService.deleteQuestionDetailById(id);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUESTION_DETAIL_S006
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}