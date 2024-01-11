package com.tip.dg4.toeic_exam.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends TExamException {
    public ForbiddenException(String message) {
        super(message);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }
}
