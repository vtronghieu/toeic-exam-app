package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.AuthenticateDto;
import com.tip.dg4.toeic_exam.dto.UserDto;
import com.tip.dg4.toeic_exam.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = TExamApiConstant.AUTH_API_ROOT)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = TExamApiConstant.AUTH_ENDPOINT_LOGIN,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> login(@RequestBody @Valid AuthenticateDto authenticateDto) {
        HttpStatus httpStatus = HttpStatus.ACCEPTED;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.AUTH_S001,
                authService.login(authenticateDto)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PostMapping(path = TExamApiConstant.AUTH_ENDPOINT_REGISTER,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> register(@RequestBody @Valid UserDto userDto) {
        authService.register(userDto);

        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.AUTH_S002
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
