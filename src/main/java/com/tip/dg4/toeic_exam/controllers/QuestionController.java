package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.QuestionRequestDto;
import com.tip.dg4.toeic_exam.dto.QuestionResponseDto;
import com.tip.dg4.toeic_exam.services.QuestionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.QUESTION_API_ROOT)
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping(path = TExamApiConstant.API_CREATE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> createQuestion(@ModelAttribute QuestionRequestDto questionRequestDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        questionService.createQuestion(questionRequestDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH})
    public ResponseEntity<ResponseData> getAllQuestions(HttpServletResponse response) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S002,
                questionService.getAllQuestions(response)
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(result, headers, httpStatus);
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

    @PostMapping(path = TExamApiConstant.GET_BY_OBJECT_TYPE_IDS_API,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getQuestionsByObjectTypeIds(@RequestBody List<UUID> objectTypeIds) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S003,
                questionService.getQuestionsByObjectTypeIds(objectTypeIds)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.TYPE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getQuestionsByType(@RequestParam(TExamParamConstant.TYPE) String type) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S003,
                questionService.getQuestionsByType(type)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.ID)
    public ResponseEntity<ResponseData> getQuestionById(@RequestParam(TExamParamConstant.ID) UUID questionId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUESTION_S006,
                questionService.getQuestionById(questionId)
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_MIXED);

        return new ResponseEntity<>(result, headers, httpStatus);
    }

    @PutMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> updateQuestion(@RequestParam(TExamParamConstant.ID) UUID questionId,
                                                       @RequestBody QuestionResponseDto questionResponseDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        questionService.updateQuestion(questionId, questionResponseDto);
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
