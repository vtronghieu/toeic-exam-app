package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.requests.RefreshTokenReq;
import com.tip.dg4.toeic_exam.dto.user.AuthenticateDto;
import com.tip.dg4.toeic_exam.dto.user.AuthorizeDto;
import com.tip.dg4.toeic_exam.dto.user.TokenDto;
import com.tip.dg4.toeic_exam.dto.user.UserDto;

public interface AuthService {
    AuthorizeDto login(AuthenticateDto authenticateDto);

    void register(UserDto userDto);

    TokenDto refreshToken(RefreshTokenReq refreshTokenReq);
}
