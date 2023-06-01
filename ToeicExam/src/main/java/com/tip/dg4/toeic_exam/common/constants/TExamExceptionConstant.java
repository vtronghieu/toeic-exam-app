package com.tip.dg4.toeic_exam.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TExamExceptionConstant {
//    Account
    public static final String ACCOUNT_E001 = "Account does not exist by username: ";
    public static final String ACCOUNT_E002 = "Account already exists by username: ";
    public static final String ACCOUNT_E003 = "Password and confirm password do not match";
    public static final String ACCOUNT_E004 = "Incorrect username or password";
}
