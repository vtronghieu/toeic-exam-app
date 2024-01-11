package com.tip.dg4.toeic_exam.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends TExamException {
    public BadRequestException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
