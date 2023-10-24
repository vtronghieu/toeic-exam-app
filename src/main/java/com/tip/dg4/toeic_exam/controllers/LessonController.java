package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.requests.LessonReq;
import com.tip.dg4.toeic_exam.services.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.LESSON_API_ROOT)
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping(path = TExamApiConstant.API_EMPTY,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    ResponseEntity<ResponseData> createLesson(@RequestBody @Valid LessonReq partLessonDto) {
        lessonService.createLesson(partLessonDto);

        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_LESSON_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseData> getLessons() {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_LESSON_S002,
                lessonService.getLessons()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.PART_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseData> getLessonsByPartId(@RequestParam(TExamParamConstant.PART_ID) UUID partId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_LESSON_S002,
                lessonService.getLessonsByPartId(partId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseData> getLessonById(@RequestParam(TExamParamConstant.ID) UUID id) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_LESSON_S005,
                lessonService.getLessonById(id)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = TExamApiConstant.API_EMPTY,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    ResponseEntity<ResponseData> updateLessonById(@RequestBody @Valid LessonReq lessonREQ) {
        lessonService.updateLessonById(lessonREQ);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_LESSON_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(path = TExamApiConstant.API_EMPTY,
                   params = TExamParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    ResponseEntity<ResponseData> deleteLessonById(@RequestParam(TExamParamConstant.ID) UUID id) {
        lessonService.deleteLessonById(id);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_LESSON_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
