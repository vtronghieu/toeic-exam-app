package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.LoginDto;
import com.tip.dg4.toeic_exam.dto.RegisterDto;
import com.tip.dg4.toeic_exam.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<ResponseData> loginAccount(@RequestBody LoginDto loginDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.ACCOUNT_S003,
                accountService.loginAccount(loginDto)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PostMapping(path = "/register", produces = "application/json")
    public ResponseEntity<ResponseData> registerAccount(@RequestBody RegisterDto registerDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.ACCOUNT_S001,
                accountService.registerAccount(registerDto)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = "/{username}", produces = "application/json")
    public ResponseEntity<ResponseData> findByUserName(@PathVariable(name = "username") String username) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.ACCOUNT_S002,
                accountService.findByUsername(username)
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
