package com.tip.dg4.toeic_exam.exceptions;

public class NotImplementedException extends TExamException {
    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }
}
