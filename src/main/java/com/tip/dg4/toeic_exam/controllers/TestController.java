package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.ParamConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
import com.tip.dg4.toeic_exam.dto.requests.TestReq;
import com.tip.dg4.toeic_exam.services.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = ApiConstant.TEST_API_ROOT)
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> createTest(@RequestBody @Valid TestReq testREQ) {
        testService.createTest(testREQ);

        HttpStatus httpStatus = HttpStatus.CREATED;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.TEST_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {ParamConstant.PAGE, ParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getTests(@RequestParam(value = ParamConstant.PAGE,
                                                               defaultValue = ParamConstant.PAGE_DEFAULT) int page,
                                                 @RequestParam(value = ParamConstant.SIZE,
                                                               defaultValue = ParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.TEST_S004,
                testService.getTests(page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getTestById(@RequestParam(ParamConstant.ID) UUID id) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.TEST_S005,
                testService.getTestById(id)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.PART_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getTestsByPartId(@RequestParam(ParamConstant.PART_ID) UUID partId) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.TEST_S004,
                testService.getTestsByPartId(partId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> updateTest(@RequestBody TestReq testReq) {
        testService.updateTest(testReq);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.TEST_S002
        );
        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = ParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> deleteTestById(@RequestParam(ParamConstant.ID) UUID id) {
        testService.deleteTestById(id);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.TEST_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}