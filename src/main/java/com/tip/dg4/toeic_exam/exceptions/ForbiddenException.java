package com.tip.dg4.toeic_exam.exceptions;

public class ForbiddenException extends TExamException {
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
