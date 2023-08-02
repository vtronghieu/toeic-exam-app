package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.ReplyAnswerDto;
import com.tip.dg4.toeic_exam.dto.SendAnswerDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.mappers.QuestionMapper;
import com.tip.dg4.toeic_exam.models.ChildQuestion;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.TestHistory;
import com.tip.dg4.toeic_exam.models.UserAnswer;
import com.tip.dg4.toeic_exam.repositories.TestHistoryRepository;
import com.tip.dg4.toeic_exam.services.ChildQuestionService;
import com.tip.dg4.toeic_exam.services.QuestionService;
import com.tip.dg4.toeic_exam.services.TestHistoryService;
import com.tip.dg4.toeic_exam.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class TestHistoryServiceImpl implements TestHistoryService {
    private final TestHistoryRepository testHistoryRepository;
    private final QuestionService questionService;
    private final UserService userService;
    private final ChildQuestionService childQuestionService;
    private final QuestionMapper questionMapper;

    public TestHistoryServiceImpl(TestHistoryRepository testHistoryRepository,
                                  QuestionService questionService,
                                  UserService userService,
                                  ChildQuestionService childQuestionService,
                                  QuestionMapper questionMapper) {
        this.testHistoryRepository = testHistoryRepository;
        this.questionService = questionService;
        this.userService = userService;
        this.childQuestionService = childQuestionService;
        this.questionMapper = questionMapper;
    }

    @Override
    public ReplyAnswerDto sendAnswer(SendAnswerDto sendAnswerDto) {
        if (!userService.existsUserById(sendAnswerDto.getUserId())) {
            throw new BadRequestException(TExamExceptionConstant.USER_E001);
        }
        Optional<Question> optionalQuestion = questionService.findById(sendAnswerDto.getQuestionId());
        if (optionalQuestion.isEmpty()) {
            throw new BadRequestException(TExamExceptionConstant.USER_E001);
        }
        Optional<ChildQuestion> optionalChildQuestion = childQuestionService.findById(sendAnswerDto.getChildQuestionId());
        if (optionalChildQuestion.isEmpty()) {
            throw new BadRequestException(TExamExceptionConstant.QUESTION_E006);
        }
        Question question = optionalQuestion.get();
        ChildQuestion childQuestion = optionalChildQuestion.get();
        ReplyAnswerDto replyAnswerDto = new ReplyAnswerDto();
        if (question.getId().equals(childQuestion.getQuestionId())){
            replyAnswerDto.setQuestionId(sendAnswerDto.getQuestionId());
            replyAnswerDto.setChildQuestionId(sendAnswerDto.getChildQuestionId());
            replyAnswerDto.setUserAnswer(sendAnswerDto.getUserAnswer());
            replyAnswerDto.setCorrectAnswer(childQuestion.getCorrectAnswer());
            replyAnswerDto.setCorrect(sendAnswerDto.getUserAnswer().equals(childQuestion.getCorrectAnswer()));
        }

        Optional<TestHistory> optionalTestHistory = testHistoryRepository.findByUserIdAndTestId(sendAnswerDto.getUserId(), question.getObjectTypeId());
        boolean isDoNotTest = optionalTestHistory.isEmpty();
        List<Question> questions = questionService.getQuestionsByObjectTypeId(question.getObjectTypeId())
                                   .stream().map(questionMapper::convertDtoToModel).toList();
        boolean isDoneTest = !isDoNotTest && (questions.size() == optionalTestHistory.get().getUserAnswers().size());
        if (isDoNotTest || isDoneTest) {
            TestHistory testHistory = new TestHistory();

            testHistory.setQuestionType(question.getType());
            testHistory.setUserId(sendAnswerDto.getUserId());
            testHistory.setTestId(question.getObjectTypeId());

            List<UserAnswer> userAnswers = new ArrayList<>();
            UserAnswer userAnswer = new UserAnswer();

            userAnswer.setQuestionId(question.getId());
            userAnswer.setChildQuestions(new ArrayList<>(Collections.singletonList(childQuestion)));
            userAnswer.setAnswerContent(sendAnswerDto.getUserAnswer());
            userAnswer.setCorrect(replyAnswerDto.isCorrect());

            userAnswers.add(userAnswer);
            testHistory.setUserAnswers(userAnswers);
            testHistoryRepository.save(testHistory);
        } else {
            TestHistory testHistory = optionalTestHistory.get();
            for (UserAnswer userAnswer : testHistory.getUserAnswers()) {
                if (userAnswer.getQuestionId().equals(sendAnswerDto.getQuestionId())) {
                    if (userAnswer.getChildQuestions().stream().anyMatch(cQuestion -> sendAnswerDto.getChildQuestionId().equals(cQuestion.getId()))) {
                        break;
                    }
                    List<UserAnswer> userAnswers = testHistory.getUserAnswers();
                    UserAnswer newUserAnswer = new UserAnswer();

                    newUserAnswer.setQuestionId(question.getId());
                    newUserAnswer.setChildQuestions(new ArrayList<>(Collections.singletonList(childQuestion)));
                    newUserAnswer.setAnswerContent(sendAnswerDto.getUserAnswer());
                    newUserAnswer.setCorrect(replyAnswerDto.isCorrect());

                    userAnswers.add(newUserAnswer);
                    testHistory.setUserAnswers(userAnswers);
                    testHistoryRepository.save(testHistory);
                } else {
                    List<UserAnswer> userAnswers = testHistory.getUserAnswers();
                    UserAnswer newUserAnswer = new UserAnswer();

                    newUserAnswer.setQuestionId(question.getId());
                    newUserAnswer.setChildQuestions(new ArrayList<>(Collections.singletonList(childQuestion)));
                    newUserAnswer.setAnswerContent(sendAnswerDto.getUserAnswer());
                    newUserAnswer.setCorrect(replyAnswerDto.isCorrect());

                    userAnswers.add(newUserAnswer);
                    testHistory.setUserAnswers(userAnswers);
                    testHistoryRepository.save(testHistory);
                    break;
                }
            }
        }

        return replyAnswerDto;
    }
}
