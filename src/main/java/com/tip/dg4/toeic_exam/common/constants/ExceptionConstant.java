package com.tip.dg4.toeic_exam.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionConstant {
    public static final String TEXAM_E001 = "Something went wrong";
    public static final String TEXAM_E002 = "Missing token";
    public static final String TEXAM_E003 = "Path not found: ";
    public static final String TEXAM_E004 = "Undefined validation error";
    public static final String TEXAM_E005 = "Invalid authentication entry point";
    public static final String TEXAM_E006 = "Invalid token";
    public static final String TEXAM_E007 = "Expired token";
    public static final String TEXAM_E008 = "Unsupported token";
    public static final String TEXAM_E009 = "Token claims string is empty";
    public static final String TEXAM_E010 = "Token signature does not match locally computed signature";
    //  Auth
    public static final String AUTH_E001 = "The username is invalid";
    public static final String AUTH_E002 = "The password is invalid";
    public static final String AUTH_E003 = "The username or password is incorrect";
    public static final String AUTH_E004 = "Don't have permission to access";
    public static final String AUTH_E005 = "Don't have permission to access";
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
    public static final String USER_INFO_E005 = "The imageURL is invalid";
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
    public static final String QUESTION_E001 = "The question not found";
    public static final String QUESTION_E002 = "The question type is invalid";
    public static final String QUESTION_E003 = "The question type is undefined";
    public static final String QUESTION_E004 = "The question level is invalid";
    public static final String QUESTION_E005 = "The question level is undefined";
    public static final String QUESTION_E006 = "The question id is invalid";
    public static final String QUESTION_E007 = "The question(s) not found";
    public static final String QUESTION_E008 = "The object type id is invalid";
    //  Question detail
    public static final String QUESTION_DETAIL_E001 = "The content question is invalid";
    public static final String QUESTION_DETAIL_E002 = "The answers is invalid";
    public static final String QUESTION_DETAIL_E003 = "The correct answer is invalid";
    public static final String QUESTION_DETAIL_E004 = "The answers must have a minimum of two answers";
    public static final String QUESTION_DETAIL_E005 = "The question details is invalid";
    public static final String QUESTION_DETAIL_E006 = "The question detail not found";
    //  Quiz test history
    public static final String QUIZ_TEST_HISTORY_E001 = "Question type must be vocabulary or grammar";
    //  Practice
    public static final String PRACTICE_E001 = "The practice already exists";
    public static final String PRACTICE_E002 = "The practice type is invalid";
    public static final String PRACTICE_E003 = "The practice not found";
    public static final String PRACTICE_E004 = "The practice name is invalid";
    public static final String PRACTICE_E005 = "The practice image is invalid";
    public static final String PRACTICE_E006 = "The practice type is undefined";
    //  Part
    public static final String PART_E001 = "The part already exists";
    public static final String PART_E002 = "The part not found";
    public static final String PART_E003 = "The practice id is invalid";
    public static final String PART_E004 = "The part name is invalid";
    public static final String PART_E005 = "The part image is invalid";
    //  Test
    public static final String TEST_E001 = "The part id is invalid";
    public static final String TEST_E002 = "The test type is invalid";
    public static final String TEST_E003 = "The test name is invalid";
    public static final String TEST_E004 = "The test already exists";
    public static final String TEST_E005 = "The test not found";
    public static final String TEST_E006 = "The test type is undefined";
    //  Lesson
    public static final String LESSON_E001 = "The lesson already exists";
    public static final String LESSON_E002 = "The lesson not found";
    public static final String LESSON_E003 = "The part id is invalid";
    public static final String LESSON_E004 = "The lesson name is invalid";
    public static final String LESSON_E005 = "The lesson not found in the part";
    //  Content
    public static final String CONTENT_E001 = "The title is invalid";
    public static final String CONTENT_E002 = "The content is invalid";
    public static final String CONTENT_E003 = "The lesson id is invalid";
    public static final String CONTENT_E004 = "The content already exists";
    public static final String CONTENT_E005 = "The content not found";
    //  Dropbox
    public static final String DROPBOX_E001 = "Invalid Access Token";
    public static final String DROPBOX_E002 = "Image name already exists";
    public static final String DROPBOX_E003 = "Please contact admin for support";
}
