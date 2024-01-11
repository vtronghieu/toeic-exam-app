package com.tip.dg4.toeic_exam.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends TExamException {
    public ConflictException(String message) {
        super(message);
        this.httpStatus = HttpStatus.CONFLICT;
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.CONFLICT;
    }
}
