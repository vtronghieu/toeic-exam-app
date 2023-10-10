package com.tip.dg4.toeic_exam.exceptions;

public class ConflictException extends TExamException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
