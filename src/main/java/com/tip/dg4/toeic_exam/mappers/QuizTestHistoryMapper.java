package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.QuizTestHistoryDto;
import com.tip.dg4.toeic_exam.dto.UserAnswerDto;
import com.tip.dg4.toeic_exam.models.enums.QuestionType;
import com.tip.dg4.toeic_exam.models.QuizTestHistory;
import com.tip.dg4.toeic_exam.models.UserAnswer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizTestHistoryMapper {
    private final UserAnswerMapper userAnswerMapper;

    public QuizTestHistoryMapper(UserAnswerMapper userAnswerMapper) {
        this.userAnswerMapper = userAnswerMapper;
    }

    public QuizTestHistory convertDtoToModel(QuizTestHistoryDto quizTestHistoryDto) {
        QuizTestHistory quizTestHistory = new QuizTestHistory();
        List<UserAnswer> userAnswerList = quizTestHistoryDto.getUserAnswers().stream().map(userAnswerMapper::convertDtoToModel).toList();

        quizTestHistory.setId(quizTestHistoryDto.getId());
        quizTestHistory.setUserId(quizTestHistoryDto.getUserId());
        quizTestHistory.setType(QuestionType.getType(quizTestHistoryDto.getType()));
        quizTestHistory.setUserAnswers(userAnswerList);

        return quizTestHistory;
    }

    public QuizTestHistoryDto convertModelToDto(QuizTestHistory quizTestHistory) {
        QuizTestHistoryDto quizTestHistoryDto = new QuizTestHistoryDto();
        List<UserAnswerDto> userAnswerDtoList = quizTestHistory.getUserAnswers().stream()
                                                .map(userAnswerMapper::convertModelToDto).toList();

        quizTestHistoryDto.setId(quizTestHistory.getId());
        quizTestHistoryDto.setUserId(quizTestHistory.getUserId());
        quizTestHistoryDto.setType(QuestionType.getValueType(quizTestHistory.getType()));
        quizTestHistoryDto.setUserAnswers(userAnswerDtoList);

        return quizTestHistoryDto;
    }
}
