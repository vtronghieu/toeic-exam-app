package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.LoginDto;
import com.tip.dg4.toeic_exam.dto.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface AccountController {
    ResponseEntity<ResponseData> loginAccount(LoginDto loginDto);

    ResponseEntity<ResponseData> registerAccount(RegisterDto registerDto);

    ResponseEntity<ResponseData> getAllAccounts();

    ResponseEntity<ResponseData> findByUsername(String username);
}
