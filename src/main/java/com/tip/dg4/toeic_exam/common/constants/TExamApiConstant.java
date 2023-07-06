package com.tip.dg4.toeic_exam.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TExamApiConstant {
    private static final String API_ROOT = "/api";
    private static final String API_VERSION_1 = "/v1";
    public static final String API_EMPTY = "";
    public static final String API_SLASH = "/";
    public static final String API_ERROR = "/error";
    public static final String ALL_API = API_ROOT + API_VERSION_1 + API_SLASH + "**";
    //    Account
    public static final String ACCOUNT_API_ROOT = API_ROOT + API_VERSION_1 + "/accounts";
    public static final String ACCOUNT_API_LOGIN = "/login";
    public static final String ACCOUNT_API_ROOT_LOGIN = ACCOUNT_API_ROOT + ACCOUNT_API_LOGIN;
    public static final String ACCOUNT_API_REGISTER = "/register";
    public static final String ACCOUNT_API_ROOT_REGISTER = ACCOUNT_API_ROOT + ACCOUNT_API_REGISTER;
}
