package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.UserInfoDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.mappers.UserInfoMapper;
import com.tip.dg4.toeic_exam.models.User;
import com.tip.dg4.toeic_exam.models.UserInfo;
import com.tip.dg4.toeic_exam.services.UserInfoService;
import com.tip.dg4.toeic_exam.services.UserService;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserService userService;
    private final UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfoDto> getUserInfos(int page, int size) {
        try {
            List<UserInfo> userInfos = this.findAll();
            if (userInfos.isEmpty()) return Collections.emptyList();

            long totalElements = userInfos.size();
            int currentPage = TExamUtil.getCorrectPage(page, size, totalElements);
            int currentSize = TExamUtil.getCorrectSize(size, totalElements);

            return TExamUtil.paginateList(userInfos, PageRequest.of(currentPage, currentSize))
                    .map(userInfoMapper::convertModelToDto)
                    .getContent();
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    @Override
    public UserInfoDto getUserInfoByUserId(UUID userId) {
        try {
            User user = userService.findById(userId)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.USER_E001));
            UserInfo userInfo = Optional.of(user.getUserInfo()).orElse(new UserInfo());

            return ObjectUtils.isNotEmpty(userInfo) ? userInfoMapper.convertModelToDto(userInfo) : new UserInfoDto();
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Updates the user information for the user with the given ID.
     *
     * @param userId      the user ID
     * @param userInfoDto the user information DTO
     * @throws NotFoundException   if the user cannot be found
     * @throws BadRequestException if the phone number is invalid
     * @throws TExamException      if an unexpected error occurs while updating the user information
     */
    @Override
    public void updateUserInfoByUserId(UUID userId, UserInfoDto userInfoDto) {
        try {
            User user = userService.findById(userId)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.USER_E001));
            if (invalidPhone(userInfoDto.getPhone())) {
                throw new BadRequestException(TExamExceptionConstant.USER_INFO_E004);
            }

            UserInfo userInfo = Optional.of(user.getUserInfo()).orElse(new UserInfo());

            userInfo.setSurname(userInfoDto.getSurname());
            userInfo.setName(userInfoDto.getName());
            userInfo.setEmail(userInfoDto.getEmail());
            userInfo.setDateOfBirth(userInfoDto.getDateOfBirth());
            userInfo.setAge(userInfoDto.getAge());
            userInfo.setAddress(userInfoDto.getAddress());
            userInfo.setPhone(userInfoDto.getPhone());
            user.setUserInfo(userInfo);

            userService.save(user);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Sets the user information for the given user information DTO.
     *
     * @param userInfoDto the user information DTO
     * @return the user information
     * @throws BadRequestException if the phone number is invalid
     */
    @Override
    public UserInfo setUserInfo(UserInfoDto userInfoDto) {
        if (invalidPhone(userInfoDto.getPhone())) {
            throw new BadRequestException(TExamExceptionConstant.USER_INFO_E004);
        }

        return userInfoMapper.convertDtoToModel(userInfoDto);
    }

    /**
     * Sets the user information for the given user information DTO, using the existing user information model as a base.
     *
     * @param userInfoDto the user information DTO
     * @param userInfo    the existing user information model
     * @return the updated user information
     * @throws BadRequestException if the phone number is invalid
     */
    @Override
    public UserInfo setUserInfo(UserInfoDto userInfoDto, UserInfo userInfo) {
        if (invalidPhone(userInfoDto.getPhone())) {
            throw new BadRequestException(TExamExceptionConstant.USER_INFO_E004);
        }

        UserInfo setedUserInfo = ObjectUtils.cloneIfPossible(userInfo);

        setedUserInfo.setId(userInfo.getId());
        setedUserInfo.setSurname(userInfo.getSurname());
        setedUserInfo.setName(userInfoDto.getName());
        setedUserInfo.setEmail(userInfoDto.getEmail());
        setedUserInfo.setDateOfBirth(userInfoDto.getDateOfBirth());
        setedUserInfo.setAge(userInfoDto.getAge());
        setedUserInfo.setAddress(userInfo.getAddress());
        setedUserInfo.setPhone(userInfo.getPhone());

        return setedUserInfo;
    }

    /**
     * Gets a list of all user info.
     *
     * @return a list of user info, or an empty list if there are no users
     */
    private List<UserInfo> findAll() {
        List<UserInfo> userInfos = userService.findAll().stream()
                .flatMap(user -> Stream.of(user.getUserInfo()))
                .toList();

        return Optional.of(userInfos).orElse(Collections.emptyList());
    }

    /**
     * Checks if a phone number is invalid.
     *
     * @param phone the phone number
     * @return true if the phone number is invalid, false otherwise
     */
    private boolean invalidPhone(String phone) {
        return !Objects.isNull(phone) && !phone.trim().matches(TExamConstant.PHONE_REGEX);
    }
}
