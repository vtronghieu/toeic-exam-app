package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.PracticeWithoutPartsDto;
import com.tip.dg4.toeic_exam.services.PracticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.PRACTICE_API_ROOT)
public class PracticeController {
    private final PracticeService practiceService;

    public PracticeController(PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @PostMapping(path = TExamApiConstant.CREATE_WITHOUT_PARTS_API,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> createPracticeWithoutParts(@RequestBody PracticeWithoutPartsDto practiceWithoutPartsDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        practiceService.createPracticeWithoutParts(practiceWithoutPartsDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_S002
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getAllPracticesWithoutParts() {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_S001,
                practiceService.getAllPracticesWithoutParts()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = TExamApiConstant.UPDATE_WITHOUT_PARTS_API,
                params = "id",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> updatePracticeWithoutParts(@RequestParam(name = "id") UUID practiceId,
                                                                   @RequestBody PracticeWithoutPartsDto practiceWithoutPartsDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        practiceService.updatePracticeWithoutParts(practiceId, practiceWithoutPartsDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(path = TExamApiConstant.DELETE_WITHOUT_PARTS_API,
                   params = "id",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> deletePracticeWithoutParts(@RequestParam(name = "id") UUID practiceId) {
        HttpStatus httpStatus = HttpStatus.OK;
        practiceService.deletePracticeWithoutParts(practiceId);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
