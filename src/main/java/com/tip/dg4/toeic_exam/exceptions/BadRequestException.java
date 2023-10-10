package com.tip.dg4.toeic_exam.exceptions;

public class BadRequestException extends TExamException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
