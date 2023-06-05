package com.tip.dg4.toeic_exam.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseError;
import com.tip.dg4.toeic_exam.exceptions.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * This class is a controller advice that handles exceptions.
 */
@Log4j2
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

    /**
     * Handles a ForbiddenException by returning a JSON response containing the error details.
     *
     * @param response:  The HTTP response object used to send the error response.
     * @param exception: The ForbiddenException that was thrown and needs to be handled.
     * @throws IOException: If an I/O error occurs while writing the response.
     */
    public void handleForbiddenException(HttpServletResponse response, ForbiddenException exception) throws IOException {
        try {
            HttpStatus httpStatus = HttpStatus.FORBIDDEN;
            ResponseError result = new ResponseError(
                    LocalDateTime.now(),
                    httpStatus.value(),
                    httpStatus.getReasonPhrase(),
                    exception.getMessage()
            );
            response.setStatus(httpStatus.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            JsonMapper mapper = new JsonMapper();
            mapper.registerModule(new JavaTimeModule());
            response.getWriter().write(mapper.writeValueAsString(result));
        } catch (IOException ioException) {
            log.error(ioException);
            throw new InternalServerErrorException(TExamExceptionConstant.TEXAM_E001);
        }
    }

    /**
     * Handles the ForbiddenException and returns a ResponseEntity with the appropriate response.
     *
     * @param exception The ForbiddenException that was thrown and needs to be handled.
     * @return A ResponseEntity object representing the error response.
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseError> handleForbiddenException(ForbiddenException exception) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ResponseError result = new ResponseError(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                exception.getMessage()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    /**
     * Handles the InternalServerErrorException and returns a ResponseEntity with the appropriate response.
     *
     * @param exception: The InternalServerErrorException that was thrown and needs to be handled.
     * @return A ResponseEntity object representing the error response.
     */
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ResponseError> handleInternalServerErrorException(InternalServerErrorException exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseError result = new ResponseError(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                exception.getMessage()
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
