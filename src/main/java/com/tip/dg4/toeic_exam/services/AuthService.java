package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.AuthenticateDto;
import com.tip.dg4.toeic_exam.dto.AuthorizeDto;
import com.tip.dg4.toeic_exam.dto.UserDto;

public interface AuthService {
    AuthorizeDto login(AuthenticateDto authenticateDto);

    void register(UserDto userDto);
}
