package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.ParamConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
import com.tip.dg4.toeic_exam.dto.user.ChangePasswordDto;
import com.tip.dg4.toeic_exam.dto.user.UserDto;
import com.tip.dg4.toeic_exam.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = ApiConstant.USER_API_ROOT)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> createUser(@RequestBody @Valid UserDto userDto) {
        userService.createUser(userDto);

        HttpStatus httpStatus = HttpStatus.CREATED;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.USER_S002
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {ParamConstant.PAGE, ParamConstant.SIZE},
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> getUsers(@RequestParam(value = ParamConstant.PAGE,
                                                               defaultValue = ParamConstant.PAGE_DEFAULT) int page,
                                                 @RequestParam(value = ParamConstant.SIZE,
                                                               defaultValue = ParamConstant.SIZE_DEFAULT) int size) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.USER_S001,
                userService.getUsers(page, size)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getUserById(@RequestParam(ParamConstant.ID) UUID id) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.USER_S003,
                userService.getUserById(id)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = ParamConstant.USERNAME,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getUserByUsername(@RequestParam(ParamConstant.USERNAME) String username) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.USER_S003,
                userService.getUserByUsername(username)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = "/change-password",
                params = ParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> changePassword(@RequestParam(ParamConstant.ID) UUID id,
                                                       @RequestBody @Valid ChangePasswordDto changePasswordDto) {
        userService.changePassword(id, changePasswordDto);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.USER_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(params = ParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> updateUserById(@RequestParam(ParamConstant.ID) UUID id,
                                                       @RequestBody @Valid UserDto userDto) {
        userService.updateUserById(id, userDto);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.USER_S005
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = ParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<DataResponse> deleteUserById(@RequestParam(ParamConstant.ID) UUID id) {
        userService.deleteUserById(id);

        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.USER_S006
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
