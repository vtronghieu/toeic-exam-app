package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.AuthenticateDto;
import com.tip.dg4.toeic_exam.dto.AuthorizeDto;
import com.tip.dg4.toeic_exam.dto.UserDto;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.exceptions.UnauthorizedException;
import com.tip.dg4.toeic_exam.models.User;
import com.tip.dg4.toeic_exam.models.enums.UserRole;
import com.tip.dg4.toeic_exam.services.AuthService;
import com.tip.dg4.toeic_exam.services.JwtService;
import com.tip.dg4.toeic_exam.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;

    /**
     * Login a user and return an authorization token.
     *
     * @param authenticateDto the authentication DTO containing the info to login.
     * @return an authorization DTO containing the info to login success.
     * @throws UnauthorizedException if the username or password is incorrect.
     * @throws TExamException        if any other error occurs.
     */
    @Override
    public AuthorizeDto login(AuthenticateDto authenticateDto) {
        try {
            User user = userService.getByUsernameAndPassword(authenticateDto.getUsername(), authenticateDto.getPassword())
                    .orElseThrow(() -> new UnauthorizedException(TExamExceptionConstant.AUTH_E003));
            String token = jwtService.generateToken(user.getUsername());

            return AuthorizeDto.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .role(UserRole.getValue(user.getRole()))
                    .token(token)
                    .build();
        } catch (Exception e) {
            throw new TExamException(TExamExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Register a new user.
     *
     * @param userDto the user DTO containing the user information.
     * @throws TExamException if any error occurs.
     */
    @Override
    public void register(UserDto userDto) {
        userService.createUser(userDto);
    }
}
