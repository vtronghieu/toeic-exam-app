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
    public static final String ACCOUNT_E006 = "Account not found";
    public static final String ACCOUNT_E007 = "Incorrect old password";
    public static final String ACCOUNT_E008 = "New password and confirm password do not match";
    //  User
    public static final String USER_E001 = "User not found";
    public static final String USER_E002 = "User not found by id: ";
    //  Vocabulary category
    public static final String VOCABULARY_CATEGORY_E001 = "Vocabulary category already exists by name: ";
    public static final String VOCABULARY_CATEGORY_E002 = "Vocabulary category not found";
    public static final String VOCABULARY_CATEGORY_E003 = "Vocabulary category not found with id: ";
    public static final String VOCABULARY_CATEGORY_E004 = "Vocabulary category not found with name: ";
    //  Vocabulary
    public static final String VOCABULARY_E001 = "Vocabulary does not exist by word: ";
    public static final String VOCABULARY_E002 = "Vocabulary does not exist by id: ";
    public static final String VOCABULARY_E003 = "Vocabulary not found";
    public static final String VOCABULARY_E004 = "Vocabulary already exists by word: ";
    public static final String VOCABULARY_E005 = "Vocabulary not found by categoryId: ";
    //  Question
    public static final String QUESTION_E001 = "Question already exists";
    public static final String QUESTION_E002 = "Question type not found";
    public static final String QUESTION_E003 = "Question not found by some resource";
    public static final String QUESTION_E004 = "Question not found by objectTypeId(s): ";
    public static final String QUESTION_E005 = "Question not found by id: ";
    public static final String QUESTION_E006 = "Question not found";
    public static final String QUESTION_E007 = "Question not found by question type: ";
    public static final String QUESTION_E008 = "Invalid question type";
    //  Quiz test history
    public static final String QUIZ_TEST_HISTORY_E001 = "Question type must be vocabulary or grammar";
    //  Practice
    public static final String PRACTICE_E001 = "Practice already exists";
    public static final String PRACTICE_E002 = "Invalid type";
    public static final String PRACTICE_E003 = "Practice not found";
}
