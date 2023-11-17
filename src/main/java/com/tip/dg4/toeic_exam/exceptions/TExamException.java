package com.tip.dg4.toeic_exam.exceptions;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TExamException extends RuntimeException {
    public TExamException(String message) {
        super(message);
    }

    public TExamException(Throwable cause) {
        super(cause);
        if (!(cause instanceof TExamException)) {
            log.error(cause, cause.fillInStackTrace());
            throw new NotImplementedException(TExamExceptionConstant.TEXAM_E001);
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