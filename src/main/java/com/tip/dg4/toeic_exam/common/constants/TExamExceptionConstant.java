package com.tip.dg4.toeic_exam.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TExamExceptionConstant {
    public static final String TEXAM_E001 = "Something went wrong";
    public static final String TEXAM_E002 = "Unauthorized, token is missing";
    public static final String TEXAM_E003 = "Path not found: ";
    //    Account
    public static final String ACCOUNT_E001 = "Account does not exist by username: ";
    public static final String ACCOUNT_E002 = "Account already exists by username: ";
    public static final String ACCOUNT_E003 = "Password and confirm password do not match";
    public static final String ACCOUNT_E004 = "Incorrect username or password";
    public static final String ACCOUNT_E005 = "This user is not have the permission to access this path";
    //  Vocabulary category
    public static final String VOCABULARY_CATEGORY_E001 = "Vocabulary category already exists by name: ";
}
