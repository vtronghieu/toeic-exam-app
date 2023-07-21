package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
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
@RequestMapping(path = TExamApiConstant.QUIZ_TEST_HISTORY_API_ROOT)
public class QuizTestHistoryController {
    private final QuizTestHistoryService quizTestHistoryService;

    public QuizTestHistoryController(QuizTestHistoryService quizTestHistoryService) {
        this.quizTestHistoryService = quizTestHistoryService;
    }

    @PostMapping(path = TExamApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> createQuizTestHistory(@RequestBody QuizTestHistoryDto quizTestHistoryDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.QUIZ_TEST_HISTORY_S001,
                quizTestHistoryService.createQuizTestHistory(quizTestHistoryDto)
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
