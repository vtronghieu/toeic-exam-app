package com.tip.dg4.toeic_exam.exceptions;

public class UnauthorizedException extends TExamException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
