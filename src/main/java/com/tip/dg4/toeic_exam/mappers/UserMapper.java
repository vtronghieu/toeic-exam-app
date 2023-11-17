package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.UserDto;
import com.tip.dg4.toeic_exam.models.User;
import com.tip.dg4.toeic_exam.models.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final UserInfoMapper userInfoMapper;

    public User convertDtoToModel(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role(UserRole.getRole(userDto.getRole()))
                .isActive(userDto.isActive())
                .userInfo(userInfoMapper.convertDtoToModel(userDto.getUserInfo()))
                .build();
    }

    public UserDto convertModelToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(UserRole.getValue(user.getRole()))
                .isActive(user.isActive())
                .userInfo(userInfoMapper.convertModelToDto(user.getUserInfo()))
                .build();
    }
}
