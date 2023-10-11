package com.tip.dg4.toeic_exam.utils;

import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Path;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
     * @param list     The list to paginate.
     * @param pageable The pagination information.
     * @return A page of the list.
     */
    public static <L> Page<L> paginateList(List<L> list, Pageable pageable) {
        Page<L> listPages = new PageImpl<>(list, pageable, list.size());

        return Optional.of(listPages).orElse(new PageImpl<>(Collections.emptyList()));
    }
}
