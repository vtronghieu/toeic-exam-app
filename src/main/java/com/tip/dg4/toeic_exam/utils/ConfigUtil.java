package com.tip.dg4.toeic_exam.utils;

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
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigUtil {
    private static RequestMappingHandlerMapping handlerMapping;
    private static Map<HttpMethod, String[]> publicRequestURIs;
    private static Map<HttpMethod, String[]> requestUriUsesRefreshToken;

    @Autowired
    public void setHandlerMapping(RequestMappingHandlerMapping handlerMapping) {
        ConfigUtil.handlerMapping = handlerMapping;
    }

    public static Map<HttpMethod, String[]> getPublicRequestURIs() {
        return ConfigUtil.publicRequestURIs;
    }

    public static void setPublicRequestURIs(Map<HttpMethod, String[]> publicRequestURIs) {
        ConfigUtil.publicRequestURIs = publicRequestURIs;
    }

    public static Map<HttpMethod, String[]> getRequestUriUsesRefreshToken() {
        return ConfigUtil.requestUriUsesRefreshToken;
    }

    public static void setRequestUriUsesRefreshToken(Map<HttpMethod, String[]> requestUriUsesRefreshTokens) {
        ConfigUtil.requestUriUsesRefreshToken = requestUriUsesRefreshTokens;
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
     * Gets the value of a field in the given object.
     *
     * @param field  The field to retrieve the value from.
     * @param source The object containing the field.
     * @return The value of the field, or null if it cannot be accessed.
     */
    public static Object getFieldValue(Field field, Object source) {
        try {
            if (field == null) return null;

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

    /**
     * Checks whether a given request URI corresponds to an existing API endpoint based on registered
     * request mappings in the application.
     * <p>
     * This method iterates through the set of {@link RequestMappingInfo} obtained from the application's
     * handler mapping. For each mapping, it retrieves the path patterns condition and checks if the provided
     * request URI matches any of the pattern values. If a match is found, the method returns {@code true},
     * indicating that the URI corresponds to an existing API endpoint; otherwise, it returns {@code false}.
     *
     * @param requestURI The request URI to be checked for existence as an API endpoint.
     * @return {@code true} if the provided request URI corresponds to an existing API endpoint, {@code false} otherwise.
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping#getHandlerMethods()
     * @see RequestMappingInfo
     */
    public static boolean existsAPI(String requestURI) {
        Set<RequestMappingInfo> requestMappingInfo = handlerMapping.getHandlerMethods().keySet();

        for (RequestMappingInfo mappingInfo : requestMappingInfo) {
            Set<String> patterns = Optional.ofNullable(mappingInfo.getPathPatternsCondition())
                    .map(PathPatternsRequestCondition::getPatternValues)
                    .orElse(Collections.emptySet());
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
    public static Map<HttpMethod, String[]> getMethodsAndAPIsByAnnotation(Class<? extends Annotation> annotation) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        Map<HttpMethod, String[]> requests = new HashMap<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            boolean hasPublicApi = AnnotationUtils.findAnnotation(entry.getValue().getMethod(), annotation) != null;
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

    /**
     * Determines if the given request URI is considered a public URI that does not require authentication.
     * <p>
     * This method checks whether the provided request URI is included in the set of URIs that are configured
     * as public URIs or if it matches any of the predefined Swagger-related URIs. Public URIs are those that
     * do not require authentication for access. The method retrieves the configured public URIs from the
     * application's configuration using ConfigUtil.getPublicRequestURIs(), which is expected to return a
     * mapping of roles to URIs.
     * <p>
     * Additionally, predefined Swagger-related URIs are included in the set of public URIs, as they typically
     * provide API documentation and do not require authentication.
     *
     * @param requestUri The request URI to be checked for being a public URI.
     * @return {@code true} if the provided request URI is a public URI, {@code false} otherwise.
     * @see ConfigUtil#getPublicRequestURIs()
     */
    public static boolean publicRequestURI(String requestUri) {
        Set<String> requestUris = ConfigUtil.getPublicRequestURIs().values()
                .parallelStream().flatMap(Arrays::stream)
                .collect(Collectors.toSet());
        Set<String> swaggerUris = Set.of(
                "/v2/api-docs",
                "/v3/api-docs",
                "/swagger-resources",
                "/swagger-config",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui",
                "/webjars"
        );

        return requestUris.contains(requestUri) || swaggerUris.stream().anyMatch(requestUri::startsWith);
    }

    /**
     * Determines if the given request URI is configured to use a refresh token for authentication.
     * <p>
     * This method checks whether the provided request URI is included in the set of URIs that are configured
     * to use refresh tokens based on the application's configuration. It retrieves the configured URIs from
     * the application's configuration using ConfigUtil.getRequestUriUsesRefreshToken(), which is expected to return
     * a mapping of roles to URIs.
     *
     * @param requestUri The request URI to be checked for using a refresh token.
     * @return {@code true} if the provided request URI is configured to use a refresh token, {@code false} otherwise.
     * @see ConfigUtil#getRequestUriUsesRefreshToken()
     */
    public static boolean isRequestUriUseRefreshToken(String requestUri) {
        Set<String> requestUris = ConfigUtil.getRequestUriUsesRefreshToken().values()
                .parallelStream().flatMap(Arrays::stream)
                .collect(Collectors.toSet());

        return requestUris.contains(requestUri);
    }
}
