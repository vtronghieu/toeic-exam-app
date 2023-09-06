package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.RegisterDto;
import com.tip.dg4.toeic_exam.dto.UserDto;

import java.util.UUID;

public interface UserService {
    void createUser(RegisterDto registerDto, UUID accountId);
    void  updateUserByUserId( UUID userId, UserDto userDto) ;
    UserDto getUserByAccountId(UUID accountId);
    boolean existsUserById(UUID userId);
}
