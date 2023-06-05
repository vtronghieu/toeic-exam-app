package com.tip.dg4.toeic_exam.exceptions;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message) {
        super(message);
    }
}
