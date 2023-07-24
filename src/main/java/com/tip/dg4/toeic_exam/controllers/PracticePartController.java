package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.PracticePartWithoutLessonsAndTestsDto;
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

    @PostMapping(path = TExamApiConstant.CREATE_WITHOUT_LESSONS_AND_TESTS_API,
                 params = "practiceId",
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> createPartWithoutLessonsAndTests(@RequestParam UUID practiceId,
                                                                         @RequestBody PracticePartWithoutLessonsAndTestsDto partWithoutLessonsAndTestsDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        practicePartService.createPartWithoutLessonsAndTests(practiceId, partWithoutLessonsAndTestsDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_PART_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.GET_WITHOUT_LESSONS_AND_TESTS_BY_PRACTICE_ID_API,
                params = "practiceId",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getPartsWithoutLessonsAndTestsByPracticeId(@RequestParam UUID practiceId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_PART_S002,
                practicePartService.getPartsWithoutLessonsAndTestsByPracticeId(practiceId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
