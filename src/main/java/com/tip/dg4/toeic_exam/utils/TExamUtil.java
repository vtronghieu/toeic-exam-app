package com.tip.dg4.toeic_exam.utils;

import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.models.QuestionType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Path;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
               .filter(cookie -> TExamConstant.ACCESS_TOKEN.equals(cookie.getName())).
                findAny().orElse(null);
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
}
