package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.RegisterDto;
import com.tip.dg4.toeic_exam.dto.UserDto;
import com.tip.dg4.toeic_exam.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User convertRegisterDtoToUser(RegisterDto registerDto) {
        User user = new User();

        user.setSurname(registerDto.getSurname());
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setDateOfBirth(registerDto.getDateOfBirth());
        user.setAddress(registerDto.getAddress());
        user.setPhone(registerDto.getPhone());
        user.setAge(registerDto.getAge());

        return user;
    }

    public UserDto convertModelToDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setSurname(user.getSurname());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setAddress(user.getAddress());
        userDto.setPhone(user.getPhone());
        userDto.setAge(user.getAge());
        userDto.setAccountId(user.getAccountId());

        return userDto;
    }
}
