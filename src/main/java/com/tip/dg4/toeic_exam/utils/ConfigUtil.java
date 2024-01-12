package com.tip.dg4.toeic_exam.utils;

import com.tip.dg4.toeic_exam.annotations.PublicApi;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Path;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigUtil {
    private static RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public void setHandlerMapping(RequestMappingHandlerMapping handlerMapping) {
        ConfigUtil.handlerMapping = handlerMapping;
    }

    /**
     * Gets the authentication cookie from the HTTP request.
     *
     * @param request The HTTP request.
     * @return The authentication cookie, or null if the cookie is not found.
     */
    public static Cookie getAuthCookie(HttpServletRequest request) {
        return Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> TExamConstant.ACCESS_TOKEN.equals(cookie.getName()))
                .findFirst().orElse(null);
    }

    /**
     * Checks if the ID field is already set in the given object.
     *
     * @param source The object to check for the presence of an ID field.
     * @return true if the ID field is not set, false otherwise.
     */
    public static boolean isObjectCreated(Object source) {
        Field idField = ConfigUtil.getIdField(source);

        return Objects.nonNull(idField) && Objects.isNull(ConfigUtil.getFieldValue(idField, source));
    }

    /**
     * Retrieves the ID field for the given object.
     *
     * @param source The object to find the ID field in.
     * @return The ID field of the object, null if not found.
     */
    public static Field getIdField(Object source) {
        return Arrays.stream(source.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst().orElse(null);
    }

    /**
     * Retrieves the Field object corresponding to the specified field name from the given object's class.
     *
     * @param source    The object from which to retrieve the Field.
     * @param fieldName The name of the field to retrieve.
     * @return The Field object representing the specified field, or null if the field is not found.
     */
    public static Field getField(Object source, String fieldName) {
        return Arrays.stream(source.getClass().getDeclaredFields())
                .filter(field -> fieldName.equals(field.getName()))
                .findFirst().orElse(null);
    }

    /**
     * Gets the value of a field in the given object.
     *
     * @param field  The field to retrieve the value from.
     * @param source The object containing the field.
     * @return The value of the field, or null if it cannot be accessed.
     */
    public static Object getFieldValue(Field field, Object source) {
        try {
            if (field == null) return null;

            field.setAccessible(true);
            return field.get(source);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Checks if a field is part of a model class within the specified package.
     *
     * @param field The field to check.
     * @return true if the field is part of a model class, false otherwise.
     */
    public static boolean isModelClassField(Field field) {
        String fieldPackage = TExamConstant.EMPTY;
        Class<?> fieldClass = field.getType();

        if (field.getType().isPrimitive() || Enum.class.isAssignableFrom(fieldClass)) {
            return false;
        } else if (Collection.class.isAssignableFrom(fieldClass)) {
            Type fieldType = field.getGenericType();
            if (fieldType instanceof ParameterizedType parameterizedType) {
                Type[] types = parameterizedType.getActualTypeArguments();
                fieldClass = (Class<?>) types[0];
                fieldPackage = fieldClass.getPackageName();
            }
        } else {
            fieldPackage = fieldClass.getPackageName();
        }

        return fieldPackage.startsWith(TExamConstant.MODEL_PACKAGE);
    }

    /**
     * Extracts the element name from the property path
     *
     * @param path The property path.
     * @return The element name, or an empty string if no element name is found.
     */
    public static String extractElementFromPropertyPath(Path path) {
        return Arrays.stream(path.toString().split(TExamConstant.DOT_REGEX))
                .filter(str -> str.contains("[")).findFirst()
                .orElse(TExamConstant.EMPTY).replaceAll(TExamConstant.DTO_REGEX, TExamConstant.EMPTY);
    }

    /**
     * Extracts the property name from the property path.
     *
     * @param path The property path.
     * @return The property name.
     */
    public static String extractPropertyFromPropertyPath(Path path) {
        String[] segments = path.toString().split(TExamConstant.DOT_REGEX);

        return segments[segments.length - 1];
    }

    public static boolean existsAPI(String requestURI) {
        Set<RequestMappingInfo> requestMappingInfo = handlerMapping.getHandlerMethods().keySet();

        for (RequestMappingInfo mappingInfo : requestMappingInfo) {
            Set<String> patterns = Objects.nonNull(mappingInfo.getPathPatternsCondition()) ?
                    mappingInfo.getPathPatternsCondition().getPatternValues() :
                    Collections.emptySet();
            boolean isApiMatch = !patterns.isEmpty() && patterns.stream().anyMatch(requestURI::equals);

            if (isApiMatch) return true;
        }

        return false;
    }

    /**
     * Retrieves methods and public APIs mapped in a Spring application.
     *
     * @return A map containing HTTP methods ({@link HttpMethod}) as keys
     * and corresponding arrays of URL patterns as values.
     */
    public static Map<HttpMethod, String[]> getMethodsAndPublicAPIs() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        Map<HttpMethod, String[]> requests = new HashMap<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            boolean hasPublicApi = AnnotationUtils.findAnnotation(entry.getValue().getMethod(), PublicApi.class) != null;
            if (!hasPublicApi) continue;
            Set<RequestMethod> requestMethods = entry.getKey().getMethodsCondition().getMethods();
            for (RequestMethod requestMethod : requestMethods) {
                HttpMethod httpMethod = HttpMethod.valueOf(requestMethod.name());
                if (!requests.containsKey(httpMethod)) {
                    requests.put(httpMethod, entry.getKey().getPatternValues().toArray(String[]::new));
                } else {
                    Set<String> paths = Arrays.stream(requests.get(httpMethod)).collect(Collectors.toSet());

                    paths.addAll(entry.getKey().getPatternValues());
                    requests.put(httpMethod, paths.toArray(String[]::new));
                }
            }
        }

        return requests;
    }
}
