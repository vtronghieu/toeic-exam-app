package com.tip.dg4.toeic_exam.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TExamApiConstant {
    private static final String API_ROOT = "/api";
    private static final String API_VERSION_1 = "/v1";
    //    Common
    public static final String API_EMPTY = "";
    public static final String API_SLASH = "/";
    public static final String API_CREATE = "/create";
    public static final String API_UPDATE = "/update";
    public static final String API_DELETE = "/delete";
    public static final String API_ERROR = "/error";
    //    All
    public static final String ALL_API = API_ROOT + API_VERSION_1 + API_SLASH + "**";
    //    Account
    public static final String ACCOUNT_API_ROOT = API_ROOT + API_VERSION_1 + "/accounts";
    public static final String ACCOUNT_API_LOGIN = "/login";
    public static final String ACCOUNT_API_ROOT_LOGIN = ACCOUNT_API_ROOT + ACCOUNT_API_LOGIN;
    public static final String ACCOUNT_API_LOGOUT = "/logout";
    public static final String ACCOUNT_API_REGISTER = "/register";
    public static final String ACCOUNT_API_ROOT_REGISTER = ACCOUNT_API_ROOT + ACCOUNT_API_REGISTER;
    //    Vocabulary category
    public static final String VOCABULARY_CATEGORY_API_ROOT = API_ROOT + API_VERSION_1 + "/vocabularyCategories";
    //    Vocabulary
    public static final String VOCABULARY_API_ROOT = API_ROOT + API_VERSION_1 + "/vocabularies";
    public static final String VOCABULARY_API_GET_BY_CATEGORY_ID = "/getByCategoryId";
    //    Vocabulary question
    public static final String VOCABULARY_QUESTION_API_ROOT = API_ROOT + API_VERSION_1 + "/vocabularyQuestions";
    public static final String VOCABULARY_QUESTION_API_GET_BY_VOCABULARY_IDS = "/getByVocabularyIds";
    public static final String VOCABULARY_QUESTION_API_SEND_VOCABULARY_ANSWERS = "/sendVocabularyAnswers";
}
