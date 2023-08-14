package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.PartTestDto;
import com.tip.dg4.toeic_exam.services.PartTestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.PART_TEST_API_ROOT)
public class PartTestController {
    private final PartTestService partTestService;

    public PartTestController(PartTestService partTestService) {
        this.partTestService = partTestService;
    }

    @PostMapping(path = TExamApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> createPartTest(@RequestBody PartTestDto partTestDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        partTestService.createPartTest(partTestDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_TEST_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = {TExamParamConstant.PRACTICE_PART_ID, TExamParamConstant.USER_ID},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getPartTestsByPartId(@RequestParam(TExamParamConstant.PRACTICE_PART_ID) UUID partId,
                                                             @RequestParam(TExamParamConstant.USER_ID) UUID userId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_TEST_S004,
                partTestService.getPartTestsByPartId(partId, userId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> updatePartTest(@RequestParam(TExamParamConstant.ID) UUID partTestId,
                                                       @RequestBody PartTestDto partTestDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        partTestService.updatePartTest(partTestId, partTestDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_TEST_S002
        );
        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(path = TExamApiConstant.API_EMPTY,
                   params = TExamParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> deletePartTestById(@RequestParam(TExamParamConstant.ID) UUID partTestId) {
        HttpStatus httpStatus = HttpStatus.OK;
        partTestService.deletePartTestById(partTestId);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_TEST_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}