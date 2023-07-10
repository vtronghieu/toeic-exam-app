package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.dto.RegisterDto;
import com.tip.dg4.toeic_exam.mappers.UserMapper;
import com.tip.dg4.toeic_exam.models.User;
import com.tip.dg4.toeic_exam.repositories.UserRepository;
import com.tip.dg4.toeic_exam.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void createUser(RegisterDto registerDto, UUID accountId) {
        User user = userMapper.convertRegisterDtoToUser(registerDto);
        user.setAccountId(accountId);
        userRepository.save(user);
    }
}
