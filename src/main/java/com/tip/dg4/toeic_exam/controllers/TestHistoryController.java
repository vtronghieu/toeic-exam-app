package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.SendAnswerDto;
import com.tip.dg4.toeic_exam.services.TestHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.TEST_HISTORY_API_ROOT)
public class TestHistoryController {
    private final TestHistoryService testHistoryService;

    public TestHistoryController(TestHistoryService testHistoryService) {
        this.testHistoryService = testHistoryService;
    }

    @PostMapping(path = TExamApiConstant.SEND_ANSWER_API,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> sendAnswer(@RequestBody SendAnswerDto sendAnswerDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.TEST_HISTORY_S001,
                testHistoryService.sendAnswer(sendAnswerDto)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = {TExamParamConstant.TEST_ID, TExamParamConstant.USER_ID},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getTestHistoriesByTestIdAndUserId(@RequestParam(TExamParamConstant.TEST_ID) UUID testId,
                                                                          @RequestParam(TExamParamConstant.USER_ID) UUID userId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.TEST_HISTORY_S002,
                testHistoryService.getTestHistoriesByTestIdAndUserId(userId, testId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
