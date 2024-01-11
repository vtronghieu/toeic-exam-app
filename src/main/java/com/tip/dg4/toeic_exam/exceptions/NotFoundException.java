package com.tip.dg4.toeic_exam.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends TExamException {
    public NotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
