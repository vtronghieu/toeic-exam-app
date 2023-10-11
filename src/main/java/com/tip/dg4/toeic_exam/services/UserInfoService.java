package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.UserInfoDto;
import com.tip.dg4.toeic_exam.models.UserInfo;

import java.util.List;
import java.util.UUID;

public interface UserInfoService {
    List<UserInfoDto> getUserInfos(int page, int size);

    UserInfoDto getUserInfoByUserId(UUID userId);

    UserInfo setUserInfo(UserInfoDto userInfoDto);

    UserInfo setUserInfo(UserInfoDto userInfoDto, UserInfo userInfo);

    void updateUserInfoByUserId(UUID userId, UserInfoDto userInfoDto);
}
