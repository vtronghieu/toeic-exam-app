package com.tip.dg4.toeic_exam.utils;

import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.models.QuestionType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class TExamUtil {

    public static String toTitleCase(String value) {
        String lowerCaseValue = value.toLowerCase();

        return lowerCaseValue.substring(0, 1).toUpperCase() + lowerCaseValue.substring(1);
    }

    public static Cookie getAuthCookie(HttpServletRequest request) {
        return Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
               .filter(cookie -> TExamConstant.ACCESS_TOKEN.equals(cookie.getName())).
                findAny().orElse(null);
    }

    public static boolean isVocabularyTypeOrGrammarType(QuestionType questionType) {
        return QuestionType.VOCABULARY.equals(questionType) || QuestionType.GRAMMAR.equals(questionType);
    }

//    public  static  void  uploadAccessTokenDropBox(String newAccessToken) throws IOException {
//
//        FileInputStream inputStream = new FileInputStream("src/main/resources/application.properties");
//        Properties properties = new Properties();
//        properties.load(inputStream);
//
//        // Thay đổi access token mới
//        properties.setProperty("dropbox.accessToken", newAccessToken);
//
//        // Ghi lại tệp application.properties với giá trị mới
//        FileOutputStream outputStream = new FileOutputStream("src/main/resources/application.properties");
//        properties.store(outputStream, null);
//        outputStream.close();
//
//    }
}
