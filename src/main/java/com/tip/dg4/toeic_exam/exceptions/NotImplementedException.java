package com.tip.dg4.toeic_exam.exceptions;

import org.springframework.http.HttpStatus;

public class NotImplementedException extends TExamException {
    public NotImplementedException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_IMPLEMENTED;
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.NOT_IMPLEMENTED;
    }
}
