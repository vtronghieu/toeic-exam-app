package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.RegisterDto;
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
}
