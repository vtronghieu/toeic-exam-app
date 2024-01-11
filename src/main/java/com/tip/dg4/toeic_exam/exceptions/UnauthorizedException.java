package com.tip.dg4.toeic_exam.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends TExamException {
    public UnauthorizedException(String message) {
        super(message);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }
}
