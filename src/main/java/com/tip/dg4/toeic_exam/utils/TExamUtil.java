package com.tip.dg4.toeic_exam.utils;

import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.models.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Path;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class TExamUtil {
    /**
     * Converts the given string to title case.
     * Title case is a style of capitalization in which the first letter of each word is capitalized.
     *
     * @param value The string to convert to title case.
     * @return The string in title case.
     */
    public static String toTitleCase(String value) {
        String lowerCaseValue = value.toLowerCase();

        return lowerCaseValue.substring(0, 1).toUpperCase() + lowerCaseValue.substring(1);
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
        Field idField = TExamUtil.getIdField(source);

        return Objects.nonNull(idField) && Objects.isNull(TExamUtil.getFieldValue(idField, source));
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

    /**
     * Corrects the page number to be within the bounds of the page range.
     *
     * @param page          The current page number.
     * @param size          The number of elements per page.
     * @param totalElements The total number of elements.
     * @return The corrected page number.
     */
    public static int getCorrectPage(int page, int size, long totalElements) {
        int totalPages = (int) Math.max((totalElements / getCorrectSize(size, totalElements)), 0);

        return Math.max(Math.min((page - 1), (totalPages - 1)), 0);
    }

    /**
     * Gets the correct size for a page, given the total number of elements.
     * This function ensures that the page size is always between 1 and the total number of elements.
     *
     * @param size          The requested size of the page.
     * @param totalElements The total number of elements.
     * @return The correct size for the page, which is always between 1 and the total number of elements.
     */
    public static int getCorrectSize(int size, long totalElements) {
        return (int) Math.max(Math.min(size, totalElements), 1);
    }

    /**
     * Paginates a list.
     *
     * @param <E>      The element.
     * @param list     The list to paginate.
     * @param pageable The pagination information.
     * @return A page of the list.
     */
    public static <E> Page<E> paginateList(List<E> list, Pageable pageable) {
        Page<E> listPages = new PageImpl<>(list, pageable, list.size());

        return Optional.of(listPages).orElse(new PageImpl<>(Collections.emptyList()));
    }

    /**
     * Constructs and returns the full name of the provided user by combining their surname and name,
     * separated by a space.
     *
     * @param user The user whose full name is to be constructed.
     * @return The full name of the provided user.
     */
    public static String getFullName(User user) {
        return user.getUserInfo().getSurname() + TExamConstant.SPACE + user.getUserInfo().getName();
    }
}
