package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.dto.StatisticDto;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.mappers.StatisticMapper;
import com.tip.dg4.toeic_exam.models.*;
import com.tip.dg4.toeic_exam.repositories.StatisticRepository;
import com.tip.dg4.toeic_exam.services.QuestionDetailService;
import com.tip.dg4.toeic_exam.services.QuestionService;
import com.tip.dg4.toeic_exam.services.StatisticService;
import com.tip.dg4.toeic_exam.services.UserService;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;
    private final QuestionService questionService;
    private final QuestionDetailService questionDetailService;
    private final UserService userService;
    private final StatisticMapper statisticMapper;

    @Override
    public List<StatisticDto> getStatistics(int page, int size) {
        try {
            long totalElements = statisticRepository.count();
            int truePage = TExamUtil.getCorrectPage(page, size, totalElements);
            int trueSize = TExamUtil.getCorrectSize(size, totalElements);
            List<Statistic> statistics = statisticRepository.findAll(PageRequest.of(truePage, trueSize))
                    .stream().collect(Collectors.toCollection(CopyOnWriteArrayList::new));

            return statistics.parallelStream().map(statisticMapper::convertModelToDto).toList();
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    //    @Override
//    public ReplyAnswerDto sendAnswer(SendAnswerDto sendAnswerDto) {
//        if (!userService.existsUserById(sendAnswerDto.getUserId())) {
//            throw new BadRequestException(TExamExceptionConstant.USER_E001);
//        }
//        Optional<Question> optionalQuestion = questionService.findById(sendAnswerDto.getQuestionId());
//        if (optionalQuestion.isEmpty()) {
//            throw new BadRequestException(TExamExceptionConstant.USER_E001);
//        }
//        Optional<QuestionDetail> optionalChildQuestion = questionDetailService.findById(sendAnswerDto.getChildQuestionId());
//        if (optionalChildQuestion.isEmpty()) {
//            throw new BadRequestException(TExamExceptionConstant.QUESTION_E006);
//        }
//        Question question = optionalQuestion.get();
//        QuestionDetail questionDetail = optionalChildQuestion.get();
//        ReplyAnswerDto replyAnswerDto = question.getId().equals(questionDetail.getQuestionId()) ?
//                                        replyAnswerMapper.convertSendAnswerDtoToDto(sendAnswerDto, questionDetail) :
//                                        new ReplyAnswerDto();
//
//        this.saveTestHistory(sendAnswerDto, question, questionDetail, replyAnswerDto);
//
//        return replyAnswerDto;
//    }
//
//    private void saveTestHistory(SendAnswerDto sendAnswerDto, Question question, QuestionDetail questionDetail, ReplyAnswerDto replyAnswerDto) {
//        Optional<Statistic> optionalTestHistory = statisticRepository.findByUserIdAndTestIdAndStatus(sendAnswerDto.getUserId(), question.getObjectTypeId(), StatisticStatus.TESTING);
//        boolean isDoNotTest = optionalTestHistory.isEmpty();
//
//        List<Question> questions = questionService.getQuestionsByObjectTypeId(question.getObjectTypeId())
//                .stream().map(questionMapper::convertDtoToModel).toList();
//
//        int totalQuestions = questions.parallelStream()
//                .mapToInt(quest -> {
//                    List<QuestionDetail> questionDetails = questionDetailService.findByQuestionId(quest.getId());
//                    return questionDetails.size();
//                })
//                .sum();
//        int totalQuestionsAnswer = 0;
//        if (!isDoNotTest) {
//            totalQuestionsAnswer = optionalTestHistory.get().getUserAnswers()
//                    .parallelStream()
//                    .mapToInt(userAnswer -> userAnswer.getQuestionDetails().size())
//                    .sum();
//        }
//        boolean isDoneTest = !isDoNotTest && (totalQuestions == totalQuestionsAnswer);
//        if (isDoNotTest || isDoneTest) {
//            this.createNewTestHistory(question, sendAnswerDto, questionDetail, replyAnswerDto);
//        } else {
//            Statistic statistic = optionalTestHistory.get();
//            this.updateTestHistory(totalQuestions, statistic, question, sendAnswerDto, questionDetail, replyAnswerDto, questions);
//        }
//    }
//
//    private void createNewTestHistory(Question question, SendAnswerDto sendAnswerDto, QuestionDetail questionDetail, ReplyAnswerDto replyAnswerDto) {
//        Statistic newStatistic = new Statistic();
//        newStatistic.setQuestionType(question.getType());
//        newStatistic.setStatus(StatisticStatus.TESTING);
//        newStatistic.setDate(LocalDate.now());
//        newStatistic.setUserId(sendAnswerDto.getUserId());
//        newStatistic.setTestId(question.getObjectTypeId());
//
//        List<UserAnswer> userAnswers = new ArrayList<>();
//        UserAnswer userAnswer = new UserAnswer();
//        userAnswer.setQuestionId(question.getId());
//        userAnswer.setQuestionDetails(new ArrayList<>(Collections.singletonList(questionDetail)));
//        userAnswer.setAnswerContent(sendAnswerDto.getUserAnswer());
//        userAnswer.setCorrect(replyAnswerDto.isCorrect());
//        userAnswers.add(userAnswer);
//
//        newStatistic.setUserAnswers(userAnswers);
//        statisticRepository.save(newStatistic);
//    }
//
//    private void updateTestHistory(int totalQuestions, Statistic statistic, Question question, SendAnswerDto sendAnswerDto, QuestionDetail questionDetail, ReplyAnswerDto replyAnswerDto, List<Question> questions) {
//        UserAnswer newUserAnswer = new UserAnswer();
//        newUserAnswer.setQuestionId(question.getId());
//        newUserAnswer.setQuestionDetails(new ArrayList<>(Collections.singletonList(questionDetail)));
//        newUserAnswer.setAnswerContent(sendAnswerDto.getUserAnswer());
//        newUserAnswer.setCorrect(replyAnswerDto.isCorrect());
//
//        List<UserAnswer> userAnswers = statistic.getUserAnswers();
//        userAnswers.add(newUserAnswer);
//
//        statistic.setUserAnswers(userAnswers);
//        if (totalQuestions == statistic.getUserAnswers().size()) {
//            statistic.setStatus(StatisticStatus.DONE);
//            statistic.setDate(LocalDate.now());
//        }
//        statisticRepository.save(statistic);
//    }
//
//    @Override
//    public List<Statistic> getTestHistoriesByTestIdAndUserId(UUID userId, UUID testId) {
//        return statisticRepository.findListByUserIdAndTestId(userId, testId);
//    }
}
