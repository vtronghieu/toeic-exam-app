package com.tip.dg4.toeic_exam.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseError;
import com.tip.dg4.toeic_exam.exceptions.*;
import com.tip.dg4.toeic_exam.utils.ApiUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@Log4j2
@ControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {
    private final RequestMappingHandlerMapping handlerMapping;

    public ExceptionConfig(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

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

    public void handleUnauthorizedException(HttpServletResponse response, UnauthorizedException exception) {
        try {
            HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
            ResponseError result = new ResponseError();
            result.setTimestamp(LocalDateTime.now());
            result.setCode(httpStatus.value());
            result.setStatus(httpStatus.getReasonPhrase());
            result.setMessage(exception.getMessage());

            JsonMapper mapper = new JsonMapper();
            mapper.registerModule(new JavaTimeModule());

            response.setStatus(httpStatus.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(mapper.writeValueAsString(result));
        } catch (IOException ioException) {
            log.error(ioException);
            throw new InternalServerErrorException(TExamExceptionConstant.TEXAM_E001);
        }
    }

    public AccessDeniedHandler handleAccessDenied() {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ResponseError result = new ResponseError(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamExceptionConstant.ACCOUNT_E005
        );

        return ((request, response, accessDeniedException) -> {
            response.setStatus(httpStatus.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            JsonMapper mapper = new JsonMapper();
            mapper.registerModule(new JavaTimeModule());
            response.getWriter().write(mapper.writeValueAsString(result));
        });
    }

    public AuthenticationEntryPoint handleAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            String requestUri = TExamApiConstant.API_ERROR.equals(request.getRequestURI()) ?
                                request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI).toString() :
                                request.getRequestURI();
            if (ApiUtil.existAPI(handlerMapping, requestUri)) return;

            HttpStatus httpStatus = HttpStatus.NOT_FOUND;
            ResponseError result = new ResponseError();
            result.setTimestamp(LocalDateTime.now());
            result.setCode(httpStatus.value());
            result.setStatus(httpStatus.getReasonPhrase());
            result.setMessage(TExamExceptionConstant.TEXAM_E003 + requestUri);

            JsonMapper mapper = new JsonMapper();
            mapper.registerModule(new JavaTimeModule());

            response.setStatus(httpStatus.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(mapper.writeValueAsString(result));
        };
    }

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

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseError> handleConflictException(ConflictException exception) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ResponseError result = new ResponseError(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                exception.getMessage()
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
