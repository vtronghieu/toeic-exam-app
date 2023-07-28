package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.ChangePasswordDto;
import com.tip.dg4.toeic_exam.dto.LoginDto;
import com.tip.dg4.toeic_exam.dto.RegisterDto;
import com.tip.dg4.toeic_exam.services.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = TExamApiConstant.ACCOUNT_API_ROOT)
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = TExamApiConstant.ACCOUNT_API_LOGIN,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> loginAccount(@RequestBody LoginDto loginDto,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.ACCOUNT_S003,
                accountService.loginAccount(loginDto, request, response)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PostMapping(path = TExamApiConstant.ACCOUNT_API_LOGOUT,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> logoutAccount(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus httpStatus = HttpStatus.OK;
        accountService.logoutAccount(request, response);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.ACCOUNT_S005
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PostMapping(path = TExamApiConstant.ACCOUNT_API_REGISTER,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> registerAccount(@RequestBody RegisterDto registerDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        accountService.registerAccount(registerDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.ACCOUNT_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
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

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = "username",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getAccountByUsername(@RequestParam String username) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.ACCOUNT_S002,
                accountService.getAccountByUsername(username)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = TExamApiConstant.ACCOUNT_API_CHANGE_PASSWORD,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> changePasswordAccount(HttpServletRequest request,
                                                              @RequestBody ChangePasswordDto changePasswordDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        accountService.changePasswordAccount(request, changePasswordDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.ACCOUNT_S006
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
