package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
import com.tip.dg4.toeic_exam.dto.QuizTestHistoryDto;
import com.tip.dg4.toeic_exam.services.QuizTestHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ApiConstant.QUIZ_TEST_HISTORY_API_ROOT)
public class QuizTestHistoryController {
    private final QuizTestHistoryService quizTestHistoryService;

    public QuizTestHistoryController(QuizTestHistoryService quizTestHistoryService) {
        this.quizTestHistoryService = quizTestHistoryService;
    }

    @PostMapping(path = ApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<DataResponse> createQuizTestHistory(@RequestBody QuizTestHistoryDto quizTestHistoryDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.QUIZ_TEST_HISTORY_S001,
                quizTestHistoryService.createQuizTestHistory(quizTestHistoryDto)
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
