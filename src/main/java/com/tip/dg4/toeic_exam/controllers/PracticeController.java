package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.requests.PracticeReq;
import com.tip.dg4.toeic_exam.services.PracticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.PRACTICE_API_ROOT)
@RequiredArgsConstructor
public class PracticeController {
    private final PracticeService practiceService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> createPractice(@RequestBody @Valid PracticeReq practiceReq) {
        practiceService.createPractice(practiceReq);

        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_S002
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getAllPractices() {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_S001,
                practiceService.getAllPractices()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = TExamParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getPracticeById(@RequestParam(TExamParamConstant.ID) UUID id) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_S001,
                practiceService.getPracticeById(id)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(params = TExamParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> updatePractice(@RequestParam(TExamParamConstant.ID) UUID id,
                                                       @RequestBody @Valid PracticeReq practiceReq) {
        practiceService.updatePractice(id, practiceReq);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = TExamParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> deletePractice(@RequestParam(TExamParamConstant.ID) UUID id) {
        practiceService.deletePractice(id);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PRACTICE_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
