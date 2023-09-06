package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.UserDto;
import com.tip.dg4.toeic_exam.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.USER_API_ROOT)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = TExamParamConstant.ACCOUNT_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getUserByAccountId(@RequestParam(TExamParamConstant.ACCOUNT_ID) UUID accountId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.USER_S001,
                userService.getUserByAccountId(accountId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }


    @PutMapping(path = TExamApiConstant.API_EMPTY,
            params = TExamParamConstant.USER_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> updateUserByUserId(@RequestParam(TExamParamConstant.USER_ID) UUID userId, @RequestBody UserDto userDto ) {
        HttpStatus httpStatus = HttpStatus.OK;
        userService.updateUserByUserId(userId,userDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.USER_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
