package com.tip.dg4.toeic_exam.exceptions;

public class InternalServerErrorException extends TExamException {
    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
