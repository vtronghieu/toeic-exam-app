package com.tip.dg4.toeic_exam.exceptions;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@Getter
public class TExamException extends RuntimeException {
    protected HttpStatus httpStatus = HttpStatus.NOT_IMPLEMENTED;

    public TExamException(String message) {
        super(message);
    }

    public TExamException(Throwable cause) {
        super(cause);
        if (!(cause instanceof TExamException)) {
            log.error(cause, cause.fillInStackTrace());
            throw new NotImplementedException(ExceptionConstant.TEXAM_E001);
        }
    }

    public TExamException(String message, Throwable cause) {
        super(message, cause);
        if (!(cause instanceof TExamException)) {
            log.error(cause, cause.fillInStackTrace());
            throw new NotImplementedException(message);
        }
    }
}