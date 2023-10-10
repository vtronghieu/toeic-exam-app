package com.tip.dg4.toeic_exam.exceptions;

public class NotFoundException extends TExamException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
