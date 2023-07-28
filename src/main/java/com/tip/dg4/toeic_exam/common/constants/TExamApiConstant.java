package com.tip.dg4.toeic_exam.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TExamApiConstant {
    private static final String API_ROOT = "/api";
    private static final String API_VERSION_1 = "/v1";
    //  Common
    public static final String API_EMPTY = "";
    public static final String API_SLASH = "/";
    public static final String API_CREATE = "/create";
    public static final String API_ERROR = "/error";
    //  All
    public static final String ALL_API = API_ROOT + API_VERSION_1 + API_SLASH + "**";
    //  Account
    public static final String ACCOUNT_API_ROOT = API_ROOT + API_VERSION_1 + "/accounts";
    public static final String ACCOUNT_API_LOGIN = "/login";
    public static final String ACCOUNT_API_ROOT_LOGIN = ACCOUNT_API_ROOT + ACCOUNT_API_LOGIN;
    public static final String ACCOUNT_API_LOGOUT = "/logout";
    public static final String ACCOUNT_API_REGISTER = "/register";
    public static final String ACCOUNT_API_CHANGE_PASSWORD = "/changePassword";
    public static final String ACCOUNT_API_ROOT_REGISTER = ACCOUNT_API_ROOT + ACCOUNT_API_REGISTER;
    //  Vocabulary category
    public static final String VOCABULARY_CATEGORY_API_ROOT = API_ROOT + API_VERSION_1 + "/vocabularyCategories";
    //  Vocabulary
    public static final String VOCABULARY_API_ROOT = API_ROOT + API_VERSION_1 + "/vocabularies";
    public static final String VOCABULARY_API_GET_BY_CATEGORY_IDS = "/getVocabulariesByCategoryId";
    //  Question
    public static final String QUESTION_API_ROOT = API_ROOT + API_VERSION_1 + "/questions";
    public static final String QUESTION_API_GET_BY_TYPE_AND_OBJECT_TYPE_IDS = "/getQuestionsByTypeAndObjectTypeIds";
    //  Quiz test history
    public static final String QUIZ_TEST_HISTORY_API_ROOT = API_ROOT + API_VERSION_1 + "/quizTestHistories";
    //  Practice
    public static final String PRACTICE_API_ROOT = API_ROOT + API_VERSION_1 + "/practices";
    //  Practice part
    public static final String PRACTICE_PART_API_ROOT = API_ROOT + API_VERSION_1 + "/practiceParts";
    //  Part test
    public static final String PART_TEST_API_ROOT = API_ROOT + API_VERSION_1 + "/partTests";
    //  Part lesson
    public static final String PART_LESSON_API_ROOT = API_ROOT + API_VERSION_1 + "/partLessons";
}

