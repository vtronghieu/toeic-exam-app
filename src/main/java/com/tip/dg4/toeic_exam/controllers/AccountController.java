package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.LoginDto;
import com.tip.dg4.toeic_exam.dto.RegisterDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = TExamApiConstant.ACCOUNT_API_ROOT)
public interface AccountController {
    @PostMapping(path = TExamApiConstant.ACCOUNT_API_LOGIN,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseData> loginAccount(@RequestBody LoginDto loginDto);

    @PostMapping(path = TExamApiConstant.ACCOUNT_API_REGISTER,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseData> registerAccount(@RequestBody RegisterDto registerDto);

    @GetMapping(path = TExamApiConstant.API_SLASH + "{username}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    ResponseEntity<ResponseData> findByUserName(@PathVariable(name = "username") String username);
}
