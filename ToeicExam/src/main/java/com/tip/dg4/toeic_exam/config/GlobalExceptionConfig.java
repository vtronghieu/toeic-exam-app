package com.tip.dg4.toeic_exam.config;

import com.tip.dg4.toeic_exam.common.responses.ResponseError;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * This class is a controller advice that handles exceptions.
 */
@ControllerAdvice
public class GlobalExceptionConfig extends ResponseEntityExceptionHandler {
    /**
     * Handles the NotFoundException and returns a ResponseEntity with the appropriate response.
     *
     * @param exception: The NotFoundException that occurred.
     * @return A ResponseEntity containing the response error.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError> handleNotFoundException(NotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ResponseError result = new ResponseError(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                exception.getMessage()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    /**
     * Handles the BadRequestException and returns a ResponseEntity with the appropriate response.
     *
     * @param exception: The BadRequestException that occurred.
     * @return A ResponseEntity containing the response error.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseError> handleBadRequestException(BadRequestException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseError result = new ResponseError(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                exception.getMessage()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    /**
     * Handles the UnauthorizedException and returns a ResponseEntity with the appropriate response.
     *
     * @param exception: The UnauthorizedException that occurred.
     * @return A ResponseEntity containing the response error.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseError> handleUnauthorizedException(UnauthorizedException exception) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ResponseError result = new ResponseError(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                exception.getMessage()
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
