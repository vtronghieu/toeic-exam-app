package com.tip.dg4.toeic_exam.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.common.responses.ErrorResponse;
import com.tip.dg4.toeic_exam.exceptions.InternalServerErrorException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.exceptions.UnauthorizedException;
import com.tip.dg4.toeic_exam.utils.ApiUtil;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionConfig extends ResponseEntityExceptionHandler {
    private final RequestMappingHandlerMapping handlerMapping;

    @ExceptionHandler(Exception.class)
    public <E extends Exception> ResponseEntity<ErrorResponse> handleException(E exception) {
        HttpStatus httpStatus = HttpStatus.NOT_IMPLEMENTED;
        String message = ExceptionConstant.TEXAM_E001;
        if ((exception instanceof TExamException)
                && (!exception.getCause().getClass().equals(TExamException.class))) {
            Field httpStatusField = TExamUtil.getField(exception, "httpStatus");
            httpStatus = (HttpStatus) Optional.ofNullable(TExamUtil.getFieldValue(httpStatusField, exception.getCause()))
                    .orElse(HttpStatus.NOT_IMPLEMENTED);
            message = exception.getCause().getMessage();
        }
        ErrorResponse result = new ErrorResponse(
                LocalDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), message
        );
        log.error(exception, exception.fillInStackTrace());

        return new ResponseEntity<>(result, httpStatus);
    }

    public void handleUnauthorizedException(HttpServletResponse response, UnauthorizedException exception) {
        try {
            HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
            ErrorResponse result = new ErrorResponse();
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
            log.error(ioException, ioException.fillInStackTrace());
            throw new InternalServerErrorException(ExceptionConstant.TEXAM_E001);
        }
    }

    public AccessDeniedHandler handleAccessDenied() {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ErrorResponse result = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                ExceptionConstant.ACCOUNT_E005
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
            String requestUri = ApiConstant.API_ERROR.equals(request.getRequestURI()) ?
                    request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI).toString() :
                    request.getRequestURI();
            if (ApiUtil.existAPI(handlerMapping, requestUri)) return;

            HttpStatus httpStatus = HttpStatus.NOT_FOUND;
            ErrorResponse result = new ErrorResponse();
            result.setTimestamp(LocalDateTime.now());
            result.setCode(httpStatus.value());
            result.setStatus(httpStatus.getReasonPhrase());
            result.setMessage(ExceptionConstant.TEXAM_E003 + requestUri);

            JsonMapper mapper = new JsonMapper();
            mapper.registerModule(new JavaTimeModule());

            response.setStatus(httpStatus.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(mapper.writeValueAsString(result));
        };
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@Nonnull MethodArgumentNotValidException exception,
                                                                  @Nonnull HttpHeaders headers,
                                                                  @Nonnull HttpStatusCode status,
                                                                  @Nonnull WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Object validationErrors = Objects.requireNonNullElse(processValidException(exception), ExceptionConstant.TEXAM_E004);
        ErrorResponse result = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                validationErrors
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Object validationErrors = Objects.requireNonNullElse(processViolationException(exception), ExceptionConstant.TEXAM_E004);
        ErrorResponse result = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                validationErrors
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    private Map<String, Object> processValidException(MethodArgumentNotValidException exception) {
        try {
            Map<String, Object> validationErrors = new WeakHashMap<>();
            for (FieldError fieldError : exception.getFieldErrors()) {
                String propertyName = fieldError.getField();
                String errorMessage = Objects.requireNonNullElse(fieldError.getDefaultMessage(), ExceptionConstant.TEXAM_E004);
                Object currentMessage = validationErrors.computeIfAbsent(propertyName, k -> errorMessage);

                if (currentMessage instanceof String) {
                    if (!Objects.equals(currentMessage, errorMessage)) {
                        currentMessage = new ArrayList<>(List.of(currentMessage, errorMessage));
                    }
                } else if (currentMessage instanceof List<?>) {
                    List<Object> messages = new ArrayList<>((List<?>) currentMessage);
                    if (!messages.contains(errorMessage)) {
                        messages.add(errorMessage);
                        currentMessage = messages;
                    }
                }
                validationErrors.put(propertyName, currentMessage);
            }

            return validationErrors;
        } catch (Exception e) {
            log.error(e, e.fillInStackTrace());
            throw new InternalServerErrorException(ExceptionConstant.TEXAM_E001);
        }
    }

    private Map<String, Map<String, Object>> processViolationException(ConstraintViolationException exception) {
        try {
            Map<String, Map<String, Object>> validationErrors = new TreeMap<>();
            for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
                String element = TExamUtil.extractElementFromPropertyPath(constraintViolation.getPropertyPath());

                if (StringUtils.isNotEmpty(element)) {
                    String propertyName = TExamUtil.extractPropertyFromPropertyPath(constraintViolation.getPropertyPath());
                    String errorMessage = constraintViolation.getMessage();
                    Map<String, Object> errorProperties = validationErrors.computeIfAbsent(element, k -> new LinkedHashMap<>());
                    Object currentMessage = errorProperties.get(propertyName);

                    if (currentMessage instanceof String && !currentMessage.equals(errorMessage)) {
                        errorProperties.put(propertyName, new ArrayList<>(List.of(currentMessage, errorMessage)));
                    } else if (currentMessage instanceof List<?>) {
                        List<Object> values = new ArrayList<>((List<?>) currentMessage);
                        if (!values.contains(errorMessage)) {
                            values.add(errorMessage);
                            errorProperties.put(propertyName, values);
                        }
                    } else {
                        errorProperties.put(propertyName, errorMessage);
                    }
                    validationErrors.put(element, errorProperties);
                }
            }

            return validationErrors;
        } catch (Exception e) {
            log.error(e, e.fillInStackTrace());
            throw new InternalServerErrorException(ExceptionConstant.TEXAM_E001);
        }
    }
}
