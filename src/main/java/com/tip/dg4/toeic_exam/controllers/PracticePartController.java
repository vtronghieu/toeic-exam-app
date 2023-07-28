package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.PracticePartDto;
import com.tip.dg4.toeic_exam.services.PracticePartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.PRACTICE_PART_API_ROOT)
public class PracticePartController {
    private final PracticePartService practicePartService;

    public PracticePartController(PracticePartService practicePartService) {
        this.practicePartService = practicePartService;
    }

    @PostMapping(path = TExamApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> createPracticePart(@RequestBody PracticePartDto partWithoutLessonsAndTestsDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        practicePartService.createPracticePart(partWithoutLessonsAndTestsDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_PART_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.PRACTICE_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getPracticePartsByPracticeId(@RequestParam(TExamParamConstant.PRACTICE_ID) UUID practiceId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_PART_S002,
                practicePartService.getPracticePartsByPracticeId(practiceId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> updatePracticePart(@RequestParam(name = TExamParamConstant.ID) UUID practicePartId,
                                                           @RequestBody PracticePartDto dtoWithoutLessonsAndTests) {
        HttpStatus httpStatus = HttpStatus.OK;
        practicePartService.updatePracticePart(practicePartId, dtoWithoutLessonsAndTests);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_PART_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(path = TExamApiConstant.API_EMPTY,
                   params = TExamParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> deletePracticePartById(@RequestParam(name = TExamParamConstant.ID) UUID practicePartId) {
        HttpStatus httpStatus = HttpStatus.OK;
        practicePartService.deletePracticePartById( practicePartId);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_PART_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
