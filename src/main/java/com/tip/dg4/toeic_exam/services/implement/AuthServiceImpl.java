package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.dto.requests.RefreshTokenReq;
import com.tip.dg4.toeic_exam.dto.user.*;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.exceptions.UnauthorizedException;
import com.tip.dg4.toeic_exam.models.User;
import com.tip.dg4.toeic_exam.models.enums.JwtType;
import com.tip.dg4.toeic_exam.services.AuthService;
import com.tip.dg4.toeic_exam.services.JwtService;
import com.tip.dg4.toeic_exam.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Login a user and return an authorization token.
     *
     * @param authenticateDto the authentication DTO containing the info to login.
     * @return an authorization DTO containing the info to login success.
     * @throws UnauthorizedException if the username or password is incorrect.
     */
    @Override
    @Transactional
    public AuthorizeDto login(AuthenticateDto authenticateDto) {
        User user = userService.getByUsernameAndPassword(authenticateDto.getUsername(), authenticateDto.getPassword())
                .orElseThrow(() -> new UnauthorizedException(ExceptionConstant.AUTH_E003));
        String accessToken = jwtService.generateToken(user.getUsername(), JwtType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(user.getUsername(), JwtType.REFRESH_TOKEN);

        user.setRefreshToken(passwordEncoder.encode(refreshToken));
        userService.save(user);

        return AuthorizeDto.builder()
                .tokens(TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build())
                .information(
                        InformationDto.builder()
                                .userId(user.getId())
                                .username(user.getUsername())
                                .role(user.getRole().getValue()).build()
                ).build();
    }

    /**
     * Register a new user.
     *
     * @param userDto the user DTO containing the user information.
     * @throws TExamException if any error occurs.
     */
    @Override
    @Transactional
    public void register(UserDto userDto) {
        userService.createUser(userDto);
    }

    @Override
    @Transactional
    public TokenDto refreshToken(RefreshTokenReq refreshTokenReq) {
        User user = userService.findByUsername(refreshTokenReq.getUsername())
                .orElseThrow(() -> new NotFoundException(ExceptionConstant.USER_E001));
        if (!passwordEncoder.matches(refreshTokenReq.getRefreshToken(), user.getRefreshToken())) {
            throw new UnauthorizedException(ExceptionConstant.TEXAM_E011);
        }

        String accessToken = jwtService.generateToken(refreshTokenReq.getUsername(), JwtType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(refreshTokenReq.getUsername(), JwtType.REFRESH_TOKEN);

        user.setRefreshToken(passwordEncoder.encode(refreshToken));
        userService.save(user);

        return TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
}
