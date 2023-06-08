package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.models.Account;

public interface AccountTokenService {
    String createToken(Account account);
}
