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
    // Auth
    public static final String AUTH_API_ROOT = API_ROOT + API_VERSION_1 + "/auth";
    public static final String AUTH_ENDPOINT_LOGIN = "/login";
    public static final String AUTH_API_LOGIN = AUTH_API_ROOT + AUTH_ENDPOINT_LOGIN;
    public static final String AUTH_ENDPOINT_REGISTER = "/register";
    public static final String AUTH_API_REGISTER = AUTH_API_ROOT + AUTH_ENDPOINT_REGISTER;
    //  User
    public static final String USER_API_ROOT = API_ROOT + API_VERSION_1 + "/users";
    //  User information
    public static final String USER_INFO_API_ROOT = API_ROOT + API_VERSION_1 + "/user-infos";
    //  Vocabulary category
    public static final String VOCABULARY_CATEGORY_API_ROOT = API_ROOT + API_VERSION_1 + "/vocabularyCategories";
    //  Vocabulary
    public static final String VOCABULARY_API_ROOT = API_ROOT + API_VERSION_1 + "/vocabularies";
    public static final String VOCABULARY_API_GET_BY_CATEGORY_IDS = "/getVocabulariesByCategoryId";
    //  Question
    public static final String QUESTION_API_ROOT = API_ROOT + API_VERSION_1 + "/questions";
    //  Question detail
    public static final String QUESTION_DETAIL_API_ROOT = API_ROOT + API_VERSION_1 + "/question-details";
    public static final String GET_BY_OBJECT_TYPE_IDS_API = "/getByObjectTypeIds";
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
    //  Test history
    public static final String TEST_HISTORY_API_ROOT = API_ROOT + API_VERSION_1 + "/testHistories";
    public static final String SEND_ANSWER_API = "/sendAnswer";
    public static final String GET_TEST_HISTORY_BY_STATUS = "/getTestHistoryByStatus";
    public static final String GET_TEST_HISTORY_OF_TEST_ID_BY_STATUS = "/getTestHistoryOfTestIdByStatus";
    public static final String UPLOAD_API_ROOT = API_ROOT + API_VERSION_1 + "/upload";
}

