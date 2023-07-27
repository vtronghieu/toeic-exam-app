package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.PartTestWithoutUserAnswerAndFinishTimeDto;
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

    @PostMapping(path = TExamApiConstant.CREATE_WITHOUT_USER_ANSWER_API,
            params = {TExamParamConstant.PRACTICE_ID, TExamParamConstant.PRACTICE_PART_ID},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> createTestWithoutUserAnswersAndFinishTime(@RequestParam(TExamParamConstant.PRACTICE_ID) UUID practiceId,
                                                                                  @RequestParam(TExamParamConstant.PRACTICE_PART_ID) UUID partId,
                                                                                  @RequestBody PartTestWithoutUserAnswerAndFinishTimeDto partTestWithoutUserAnswerAndFinishTimeDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        partTestService.createTest(practiceId, partId, partTestWithoutUserAnswerAndFinishTimeDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_TEST_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
            params = TExamParamConstant.PRACTICE_PART_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getTestsByPartId(@RequestParam(TExamParamConstant.PRACTICE_PART_ID) UUID partId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_TEST_S001,
                partTestService.getTestsByPartId(partId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}