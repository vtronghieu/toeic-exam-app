package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.AccountDto;
import com.tip.dg4.toeic_exam.dto.RegisterDto;

public interface AccountService {
    AccountDto registerAccount(RegisterDto registerDto);
}
