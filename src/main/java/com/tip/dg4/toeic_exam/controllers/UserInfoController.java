package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.ParamConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
import com.tip.dg4.toeic_exam.dto.user.UserInfoDto;
import com.tip.dg4.toeic_exam.services.UserInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = ApiConstant.USER_INFO_API_ROOT)
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping(params = {ParamConstant.PAGE, ParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> getUserInfos(@RequestParam(value = ParamConstant.PAGE,
                                                                   defaultValue = ParamConstant.PAGE_DEFAULT) int page,
                                                     @RequestParam(value = ParamConstant.SIZE,
                                                                   defaultValue = ParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.USER_INFO_S002,
                userInfoService.getUserInfos(page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.USER_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getUserInfoByUserId(@RequestParam(ParamConstant.USER_ID) UUID userId) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.USER_INFO_S001,
                userInfoService.getUserInfoByUserId(userId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(params = ParamConstant.USER_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> updateUserInfoByUserId(@RequestParam(ParamConstant.USER_ID) UUID userId,
                                                               @RequestBody @Valid UserInfoDto userInfoDto) {
        userInfoService.updateUserInfoByUserId(userId, userInfoDto);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.USER_INFO_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
