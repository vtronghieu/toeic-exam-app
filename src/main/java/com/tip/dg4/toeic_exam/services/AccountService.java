package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.AccountDto;
import com.tip.dg4.toeic_exam.dto.AuthorizationDto;
import com.tip.dg4.toeic_exam.dto.LoginDto;
import com.tip.dg4.toeic_exam.dto.RegisterDto;

import java.util.List;

public interface AccountService {
    AuthorizationDto loginAccount(LoginDto loginDto);
    void registerAccount(RegisterDto registerDto);
    List<AccountDto> getAllAccounts();
    AccountDto getAccountByUsername(String username);
}
