package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.user.UserInfoDto;
import com.tip.dg4.toeic_exam.models.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper {
    public UserInfo convertDtoToModel(UserInfoDto userInfoDto) {
        return UserInfo.builder()
                .id(userInfoDto.getId())
                .surname(userInfoDto.getSurname())
                .name(userInfoDto.getName())
                .email(userInfoDto.getEmail())
                .imageURL(userInfoDto.getImageURL())
                .dateOfBirth(userInfoDto.getDateOfBirth())
                .age(userInfoDto.getAge())
                .address(userInfoDto.getAddress())
                .phone(userInfoDto.getPhone())
                .build();
    }

    public UserInfoDto convertModelToDto(UserInfo userInfo) {
        return UserInfoDto.builder()
                .id(userInfo.getId())
                .surname(userInfo.getSurname())
                .name(userInfo.getName())
                .imageURL(userInfo.getImageURL())
                .email(userInfo.getEmail())
                .dateOfBirth(userInfo.getDateOfBirth())
                .age(userInfo.getAge())
                .address(userInfo.getAddress())
                .phone(userInfo.getPhone())
                .build();
    }
}
