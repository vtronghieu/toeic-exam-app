package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.AccountDto;
import com.tip.dg4.toeic_exam.dto.LoginDto;
import com.tip.dg4.toeic_exam.dto.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountService {
    String loginAccount(LoginDto loginDto);
    void logoutAccount(HttpServletRequest request, HttpServletResponse response);
    AccountDto registerAccount(RegisterDto registerDto);
    AccountDto findByUsername(String username);
}
