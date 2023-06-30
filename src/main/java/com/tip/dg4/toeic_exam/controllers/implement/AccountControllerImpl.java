package com.tip.dg4.toeic_exam.controllers.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.controllers.AccountController;
import com.tip.dg4.toeic_exam.dto.LoginDto;
import com.tip.dg4.toeic_exam.dto.RegisterDto;
import com.tip.dg4.toeic_exam.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = TExamApiConstant.GLOBAL_URL)
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;

    public AccountControllerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<ResponseData> loginAccount(LoginDto loginDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.ACCOUNT_S003,
                accountService.loginAccount(loginDto)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @Override
    public ResponseEntity<ResponseData> registerAccount(RegisterDto registerDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.ACCOUNT_S001,
                accountService.registerAccount(registerDto)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @Override
    public ResponseEntity<ResponseData> getAllAccounts() {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.ACCOUNT_S004,
                accountService.getAllAccounts()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @Override
    public ResponseEntity<ResponseData> findByUserName(String username) {
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
