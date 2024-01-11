package com.tip.dg4.toeic_exam.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends TExamException {
    public InternalServerErrorException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
