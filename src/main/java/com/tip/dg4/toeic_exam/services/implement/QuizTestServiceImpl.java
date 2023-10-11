package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.dto.QuizTestHistoryDto;
import com.tip.dg4.toeic_exam.mappers.QuizTestHistoryMapper;
import com.tip.dg4.toeic_exam.repositories.QuizTestHistoryRepository;
import com.tip.dg4.toeic_exam.services.QuestionService;
import com.tip.dg4.toeic_exam.services.QuizTestHistoryService;
import com.tip.dg4.toeic_exam.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class QuizTestServiceImpl implements QuizTestHistoryService {
    private final QuizTestHistoryRepository quizTestHistoryRepository;
    private final QuizTestHistoryMapper quizTestHistoryMapper;
    private final UserService userService;
    private final QuestionService questionService;

    public QuizTestServiceImpl(QuizTestHistoryRepository quizTestHistoryRepository,
                               QuizTestHistoryMapper quizTestHistoryMapper,
                               UserService userService,
                               QuestionService questionService) {
        this.quizTestHistoryRepository = quizTestHistoryRepository;
        this.quizTestHistoryMapper = quizTestHistoryMapper;
        this.userService = userService;
        this.questionService = questionService;
    }

    @Override
    public QuizTestHistoryDto createQuizTestHistory(QuizTestHistoryDto quizTestHistoryDto) {
//        if (!userService.existsUserById(quizTestHistoryDto.getUserId())) {
//            log.error(TExamExceptionConstant.USER_E002 + quizTestHistoryDto.getUserId());
//            throw new NotFoundException(TExamExceptionConstant.USER_E001);
//        }
//        QuestionType questionType = QuestionType.getType(quizTestHistoryDto.getType());
//        if (Objects.isNull(questionType) || !TExamUtil.isVocabularyTypeOrGrammarType(questionType)) {
//            throw new BadRequestException(TExamExceptionConstant.QUIZ_TEST_HISTORY_E001);
//        }
//
//        QuizTestHistory quizTestHistory = quizTestHistoryMapper.convertDtoToModel(quizTestHistoryDto);
//        for (UserAnswer userAnswer : quizTestHistory.getUserAnswers()) {
//            Optional<Question> optionalQuestion = questionService.findByTypeAndId(questionType, userAnswer.getQuestionId());
//            if (optionalQuestion.isEmpty()) {
//                log.error(TExamExceptionConstant.QUESTION_E005 + userAnswer.getQuestionId());
//                throw new NotFoundException(TExamExceptionConstant.QUESTION_E006);
//            }
//
//            Question question = optionalQuestion.get();
//            boolean isCorrect = Objects.equals(userAnswer.getOptionAnswer(), question.getOptionAnswers().getCorrectAnswer());
//            userAnswer.setIsCorrect(isCorrect);
//        }
//        quizTestHistoryRepository.save(quizTestHistory);
//
//        return quizTestHistoryMapper.convertModelToDto(quizTestHistory);
        return null;
    }
}
