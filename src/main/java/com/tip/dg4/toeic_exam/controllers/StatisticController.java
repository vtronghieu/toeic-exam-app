package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.ParamConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
import com.tip.dg4.toeic_exam.dto.question.SendAnswerDto;
import com.tip.dg4.toeic_exam.services.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = ApiConstant.STATISTIC_API_ROOT)
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping(params = {ParamConstant.PAGE, ParamConstant.SIZE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getStatistics(@RequestParam(name = ParamConstant.PAGE,
                                                                    defaultValue = ParamConstant.PAGE_DEFAULT) int page,
                                                      @RequestParam(name = ParamConstant.SIZE,
                                                                    defaultValue = ParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.STATISTIC_S001,
                statisticService.getStatistics(page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PostMapping(path = ApiConstant.SEND_ANSWER_API,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> sendAnswer(@RequestBody SendAnswerDto sendAnswerDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.STATISTIC_S002
//                statisticService.sendAnswer(sendAnswerDto)
        );

        return new ResponseEntity<>(result, httpStatus);
    }
//
//    @GetMapping(path = TExamApiConstant.API_EMPTY,
//                params = {TExamParamConstant.TEST_ID, TExamParamConstant.USER_ID},
//                produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResponseData> getTestHistoriesByTestIdAndUserId(@RequestParam(TExamParamConstant.TEST_ID) UUID testId,
//                                                                          @RequestParam(TExamParamConstant.USER_ID) UUID userId) {
//        HttpStatus httpStatus = HttpStatus.OK;
//        ResponseData result = new ResponseData(
//                httpStatus.value(),
//                httpStatus.getReasonPhrase(),
//                TExamSuccessfulConstant.TEST_HISTORY_S002,
//                statisticService.getTestHistoriesByTestIdAndUserId(userId, testId)
//        );
//
//        return new ResponseEntity<>(result, httpStatus);
//    }
}
