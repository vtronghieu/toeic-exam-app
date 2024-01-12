package com.tip.dg4.toeic_exam.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.common.responses.ErrorResponse;
import com.tip.dg4.toeic_exam.exceptions.InternalServerErrorException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.utils.ConfigUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionConfig extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TExamException.class)
    public <E extends TExamException> ResponseEntity<ErrorResponse> handleTExamException(E exception) {
        Field httpStatusField = ConfigUtil.getField(exception, "httpStatus");
        HttpStatus httpStatus = (HttpStatus) Optional.ofNullable(ConfigUtil.getFieldValue(httpStatusField, exception.getCause()))
                .orElse(HttpStatus.NOT_IMPLEMENTED);
        String message = httpStatus != HttpStatus.NOT_IMPLEMENTED ? exception.getCause().getMessage() : exception.getMessage();
        ErrorResponse result = new ErrorResponse(
                LocalDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), message
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    public void handleJwtException(HttpServletResponse response, String message, String... requestURI) {
        try {
            HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
            if (requestURI != null && requestURI.length > 0) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = ExceptionConstant.TEXAM_E003 + requestURI[0];
            }
            ErrorResponse result = new ErrorResponse(
                    LocalDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), message
            );
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
        return ((request, response, accessDeniedException) -> {
            HttpStatus httpStatus = HttpStatus.FORBIDDEN;
            ErrorResponse result = new ErrorResponse(
                    LocalDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), ExceptionConstant.AUTH_E004
            );
            JsonMapper mapper = new JsonMapper();
            mapper.registerModule(new JavaTimeModule());

            response.setStatus(httpStatus.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(mapper.writeValueAsString(result));
        });
    }

    public AuthenticationEntryPoint handleAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
            LocalDateTime currentDateTime = LocalDateTime.now();
            String message;
            if (httpStatus == HttpStatus.OK) {
                if (!ConfigUtil.existsAPI(request.getRequestURI())) {
                    httpStatus = HttpStatus.NOT_FOUND;
                    message = ExceptionConstant.TEXAM_E003 + request.getRequestURI();
                } else {
                    return;
                }
            } else if (httpStatus == HttpStatus.NOT_FOUND) {
                String requestUri = ApiConstant.API_ERROR.equals(request.getRequestURI())
                        ? request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI).toString()
                        : request.getRequestURI();
                message = ExceptionConstant.TEXAM_E003 + requestUri;
            } else if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
                message = ExceptionConstant.TEXAM_E001;
                log.error(authException, authException.fillInStackTrace());
            } else {
                message = ExceptionConstant.TEXAM_E005;
            }
            ErrorResponse result = new ErrorResponse(
                    currentDateTime, httpStatus.value(), httpStatus.getReasonPhrase(), message
            );
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
                String element = ConfigUtil.extractElementFromPropertyPath(constraintViolation.getPropertyPath());

                if (StringUtils.isNotEmpty(element)) {
                    String propertyName = ConfigUtil.extractPropertyFromPropertyPath(constraintViolation.getPropertyPath());
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
