package com.tip.dg4.toeic_exam.utils;

import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.models.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
