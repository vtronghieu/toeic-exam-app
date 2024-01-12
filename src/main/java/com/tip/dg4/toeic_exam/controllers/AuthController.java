package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.annotations.PublicApi;
import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
import com.tip.dg4.toeic_exam.dto.user.AuthenticateDto;
import com.tip.dg4.toeic_exam.dto.user.UserDto;
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
@RequestMapping(path = ApiConstant.AUTH_API_ROOT)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PublicApi
    @PostMapping(path = ApiConstant.AUTH_ENDPOINT_LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> login(@RequestBody @Valid AuthenticateDto authenticateDto) {
        HttpStatus httpStatus = HttpStatus.ACCEPTED;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.AUTH_S001,
                authService.login(authenticateDto)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PublicApi
    @PostMapping(path = ApiConstant.AUTH_ENDPOINT_REGISTER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> register(@RequestBody @Valid UserDto userDto) {
        authService.register(userDto);

        HttpStatus httpStatus = HttpStatus.CREATED;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.AUTH_S002
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
