package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.ContentDto;
import com.tip.dg4.toeic_exam.services.ContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(TExamApiConstant.CONTENT_API_ROOT)
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @PostMapping(path = TExamApiConstant.API_EMPTY,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> createContent(@RequestBody @Valid ContentDto contentDTO) {
        contentService.createContent(contentDTO);

        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.CONTENT_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getContents() {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.CONTENT_S002,
                contentService.getContents()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getContentById(@RequestParam(TExamParamConstant.ID) UUID id) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.CONTENT_S003,
                contentService.getContentById(id)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.LESSON_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getContentsByLessonId(@RequestParam(TExamParamConstant.LESSON_ID) UUID lessonId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.CONTENT_S002,
                contentService.getContentsByLessonId(lessonId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = TExamApiConstant.API_EMPTY,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> updateContent(@RequestBody @Valid ContentDto contentDTO) {
        contentService.updateContent(contentDTO);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.CONTENT_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(path = TExamApiConstant.API_EMPTY,
                   params = TExamParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> deleteContentById(@RequestParam(TExamParamConstant.ID) UUID id) {
        contentService.deleteContentById(id);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.CONTENT_S005
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
