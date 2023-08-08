package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.PartLessonDto;
import com.tip.dg4.toeic_exam.services.PartLessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.PART_LESSON_API_ROOT)
public class PartLessonController {
    private final PartLessonService partLessonService;

    public PartLessonController(PartLessonService partLessonService) {
        this.partLessonService = partLessonService;
    }

    @PostMapping(path = TExamApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    ResponseEntity<ResponseData> createPartLesson(@RequestBody PartLessonDto partLessonDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        partLessonService.createPartLesson(partLessonDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_LESSON_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.PRACTICE_PART_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseData> getPartLessonsByPartId(@RequestParam(TExamParamConstant.PRACTICE_PART_ID) UUID practicePartId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_LESSON_S002,
                partLessonService.getPartLessonsByPartId(practicePartId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }


    @GetMapping(path = TExamApiConstant.API_EMPTY,
            params = {TExamParamConstant.PRACTICE_PART_ID, TExamParamConstant.PART_LESSON_ID},
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseData> getPartLessonsById(@RequestParam(TExamParamConstant.PRACTICE_PART_ID) UUID practicePartId,
                                                    @RequestParam(TExamParamConstant.PART_LESSON_ID) UUID partLessonId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_LESSON_S002,
                partLessonService.getPartLessonsById(practicePartId, partLessonId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    ResponseEntity<ResponseData> updatePartLesson(@RequestParam(TExamParamConstant.ID) UUID partLessonId,
                                                  @RequestBody PartLessonDto partLessonDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        partLessonService.updatePartLesson(partLessonId, partLessonDto);
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
    @PreAuthorize("hasAuthority('admin')")
    ResponseEntity<ResponseData> deletePartLesson(@RequestParam(TExamParamConstant.ID) UUID partLessonId) {
        HttpStatus httpStatus = HttpStatus.OK;
        partLessonService.deletePartLesson(partLessonId);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_LESSON_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
