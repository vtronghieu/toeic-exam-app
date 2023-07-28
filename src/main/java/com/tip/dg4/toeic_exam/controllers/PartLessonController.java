package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
<<<<<<< Updated upstream
import com.tip.dg4.toeic_exam.dto.PartLessonWithoutContentsDto;
=======
import com.tip.dg4.toeic_exam.dto.PartLessonDto;
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    @PostMapping(path = TExamApiConstant.CREATE_WITHOUT_CONTENTS_API,
                 params = {TExamParamConstant.PRACTICE_ID, TExamParamConstant.PRACTICE_PART_ID},
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    ResponseEntity<ResponseData> createLessonWithoutContents(@RequestParam(TExamParamConstant.PRACTICE_ID) UUID practiceId,
                                                             @RequestParam(TExamParamConstant.PRACTICE_PART_ID) UUID practicePartId,
                                                             @RequestBody PartLessonWithoutContentsDto partLessonWithoutContentsDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        partLessonService.createLessonWithoutContents(practiceId, practicePartId, partLessonWithoutContentsDto);
=======
    @PostMapping(path = TExamApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    ResponseEntity<ResponseData> createPartLesson(@RequestBody PartLessonDto partLessonDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        partLessonService.createPartLesson(partLessonDto);
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    ResponseEntity<ResponseData> getLessonsWithoutContentsByPartId(@RequestParam(TExamParamConstant.PRACTICE_PART_ID) UUID practicePartId) {
        HttpStatus httpStatus = HttpStatus.CREATED;
=======
    ResponseEntity<ResponseData> getPartLessonsByPartId(@RequestParam(TExamParamConstant.PRACTICE_PART_ID) UUID practicePartId) {
        HttpStatus httpStatus = HttpStatus.OK;
>>>>>>> Stashed changes
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_LESSON_S002,
<<<<<<< Updated upstream
                partLessonService.getLessonsWithoutContentsByPartId(practicePartId)
=======
                partLessonService.getPartLessonsByPartId(practicePartId)
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
>>>>>>> Stashed changes
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
