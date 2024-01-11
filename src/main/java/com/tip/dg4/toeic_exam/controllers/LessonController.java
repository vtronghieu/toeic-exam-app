package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.ParamConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
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
@RequestMapping(path = ApiConstant.LESSON_API_ROOT)
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    ResponseEntity<DataResponse> createLesson(@RequestBody @Valid LessonReq partLessonDto) {
        lessonService.createLesson(partLessonDto);

        HttpStatus httpStatus = HttpStatus.CREATED;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.LESSON_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DataResponse> getLessons() {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.LESSON_S002,
                lessonService.getLessons()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.PART_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DataResponse> getLessonsByPartId(@RequestParam(ParamConstant.PART_ID) UUID partId) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.LESSON_S002,
                lessonService.getLessonsByPartId(partId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DataResponse> getLessonById(@RequestParam(ParamConstant.ID) UUID id) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.LESSON_S005,
                lessonService.getLessonById(id)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    ResponseEntity<DataResponse> updateLessonById(@RequestBody @Valid LessonReq lessonREQ) {
        lessonService.updateLessonById(lessonREQ);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.LESSON_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = ParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    ResponseEntity<DataResponse> deleteLessonById(@RequestParam(ParamConstant.ID) UUID id) {
        lessonService.deleteLessonById(id);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.LESSON_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
