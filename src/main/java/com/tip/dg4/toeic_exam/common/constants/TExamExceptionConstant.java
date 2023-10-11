package com.tip.dg4.toeic_exam.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TExamExceptionConstant {
    public static final String TEXAM_E001 = "Something went wrong";
    public static final String TEXAM_E002 = "Unauthorized, token is missing";
    public static final String TEXAM_E003 = "Path not found: ";
    public static final String TEXAM_E004 = "Undefined validation error";
    //    Account
    public static final String ACCOUNT_E005 = "This user is not have the permission to access this path";
    //  Auth
    public static final String AUTH_E001 = "The username is invalid";
    public static final String AUTH_E002 = "The password is invalid";
    public static final String AUTH_E003 = "The username or password is incorrect";
    public static final String AUTH_E004 = "The user doesn't have access";
    //  User
    public static final String USER_E001 = "The user not found";
    public static final String USER_E002 = "The user already exists";
    public static final String USER_E003 = "The user role is undefined";
    public static final String USER_E004 = "The user role is invalid";
    public static final String USER_E005 = "The old password is invalid";
    public static final String USER_E006 = "The new password is invalid";
    public static final String USER_E007 = "The confirm new password is invalid";
    public static final String USER_E008 = "The old password is incorrect";
    public static final String USER_E009 = "The new password and confirm new password don't match";
    //  User information
    public static final String USER_INFO_E001 = "The surname is invalid";
    public static final String USER_INFO_E002 = "The name is invalid";
    public static final String USER_INFO_E003 = "The email is invalid";
    public static final String USER_INFO_E004 = "The phone is invalid";
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
    public static final String QUESTION_E002 = "Question type invalid";
    public static final String QUESTION_E005 = "Question not found by id: ";
    public static final String QUESTION_E006 = "Question not found";
    //  Quiz test history
    public static final String QUIZ_TEST_HISTORY_E001 = "Question type must be vocabulary or grammar";
    //  Practice
    public static final String PRACTICE_E001 = "Practice name already exists";
    public static final String PRACTICE_E002 = "Practice type invalid";
    public static final String PRACTICE_E003 = "Practice not found";
    //  Practice part
    public static final String PRACTICE_PART_E001 = "Practice part name already exists";
    public static final String PRACTICE_PART_E002 = "Practice part not found";
    //  Part test
    public static final String PART_TEST_E001 = "Part test name already exists";
    public static final String PART_TEST_E002 = "Part test type invalid";
    public static final String PART_TEST_E003 = "Part test not found";
    //  Part lesson
    public static final String PART_LESSON_E001 = "Part lesson name already exists";
    public static final String PART_LESSON_E002 = "Part lesson not found";

    public static final String DROPBOX_E001 = "Invalid Access Token";
    public static final String DROPBOX_E002 = "Image name already exists";
    public static final String DROPBOX_E003 = "Please contact admin for support";
}
