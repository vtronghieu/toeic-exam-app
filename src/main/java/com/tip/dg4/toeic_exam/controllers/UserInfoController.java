package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.UserInfoDto;
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
@RequestMapping(path = TExamApiConstant.USER_INFO_API_ROOT)
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping(params = {TExamParamConstant.PAGE, TExamParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> getUserInfos(@RequestParam(value = TExamParamConstant.PAGE,
                                                                   defaultValue = TExamParamConstant.PAGE_DEFAULT) int page,
                                                     @RequestParam(value = TExamParamConstant.SIZE,
                                                                   defaultValue = TExamParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.USER_INFO_S002,
                userInfoService.getUserInfos(page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = TExamParamConstant.USER_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getUserInfoByUserId(@RequestParam(TExamParamConstant.USER_ID) UUID userId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.USER_INFO_S001,
                userInfoService.getUserInfoByUserId(userId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(params = TExamParamConstant.USER_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> updateUserInfoByUserId(@RequestParam(TExamParamConstant.USER_ID) UUID userId,
                                                               @RequestBody @Valid UserInfoDto userInfoDto) {
        userInfoService.updateUserInfoByUserId(userId, userInfoDto);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.USER_INFO_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
