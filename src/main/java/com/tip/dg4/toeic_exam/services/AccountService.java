package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface AccountService {
    AuthenticationDto loginAccount(LoginDto loginDto);
    void logoutAccount(HttpServletRequest request, HttpServletResponse response);
    void registerAccount(RegisterDto registerDto);
    List<AccountDto> getAllAccounts();
    AccountDto getAccountByUsername(String username);
    void changePasswordAccount(HttpServletRequest request, ChangePasswordDto changePasswordDto);
}
