package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.RegisterDto;

import java.util.UUID;

public interface UserService {
    void createUser(RegisterDto registerDto, UUID accountId);
    boolean existsUserById(UUID userId);
}
