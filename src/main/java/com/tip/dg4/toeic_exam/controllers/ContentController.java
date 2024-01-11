package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.ParamConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
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
@RequestMapping(ApiConstant.CONTENT_API_ROOT)
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> createContent(@RequestBody @Valid ContentDto contentDTO) {
        contentService.createContent(contentDTO);

        HttpStatus httpStatus = HttpStatus.CREATED;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.CONTENT_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getContents() {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.CONTENT_S002,
                contentService.getContents()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getContentById(@RequestParam(ParamConstant.ID) UUID id) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.CONTENT_S003,
                contentService.getContentById(id)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.LESSON_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getContentsByLessonId(@RequestParam(ParamConstant.LESSON_ID) UUID lessonId) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.CONTENT_S002,
                contentService.getContentsByLessonId(lessonId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> updateContent(@RequestBody @Valid ContentDto contentDTO) {
        contentService.updateContent(contentDTO);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.CONTENT_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = ParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> deleteContentById(@RequestParam(ParamConstant.ID) UUID id) {
        contentService.deleteContentById(id);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.CONTENT_S005
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
