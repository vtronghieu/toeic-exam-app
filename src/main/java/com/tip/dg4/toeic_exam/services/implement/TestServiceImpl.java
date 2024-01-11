package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.dto.TestDto;
import com.tip.dg4.toeic_exam.dto.requests.TestReq;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.mappers.TestMapper;
import com.tip.dg4.toeic_exam.models.Part;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.Test;
import com.tip.dg4.toeic_exam.models.enums.PracticeType;
import com.tip.dg4.toeic_exam.services.PartService;
import com.tip.dg4.toeic_exam.services.PracticeService;
import com.tip.dg4.toeic_exam.services.QuestionService;
import com.tip.dg4.toeic_exam.services.TestService;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final PracticeService practiceService;
    private final PartService partService;
    private final QuestionService questionService;
    private final TestMapper testMapper;

    /**
     * Creates a new test.
     *
     * @param testREQ the test request
     * @throws NotFoundException   if the part does not exist
     * @throws BadRequestException if the type of the test is invalid, or if a test with the same name already exists in the part
     */
    @Override
    public void createTest(TestReq testREQ) {
        UUID newTestID = null;
        try {
            Part part = partService.findById(testREQ.getPartId())
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.PART_E002));
            PracticeType practiceType = PracticeType.getType(testREQ.getType());
            if (Objects.equals(PracticeType.UNDEFINED, practiceType)) {
                throw new BadRequestException(ExceptionConstant.PRACTICE_E006);
            }
            if (existsInPartByName(part, testREQ.getName())) {
                throw new BadRequestException(ExceptionConstant.TEST_E004);
            }

            List<Test> tests = Optional.ofNullable(part.getTests()).orElse(new ArrayList<>());
            Test newTest = testMapper.convertReqToModel(testREQ);

            tests.add(newTest);
            part.setTests(tests);
            practiceService.saveByPart(part);// The first save to generate ID for the test
            newTestID = newTest.getId();
            if (!CollectionUtils.isEmpty(testREQ.getQuestions())) {
                tests.stream()
                        .filter(test -> testREQ.getName().equals(test.getName()))
                        .findFirst()
                        .ifPresent(test -> {
                            List<Question> questions = questionService.createQuestions(test, testREQ.getQuestions());
                            List<UUID> questionIDs = Optional.ofNullable(test.getQuestionIDs()).orElse(new ArrayList<>());
                            List<UUID> newQuestionIDs = questionService.getQuestionIDsByQuestions(questions);

                            questionIDs.addAll(newQuestionIDs);
                            test.setQuestionIDs(questionIDs);
                        });
                part.setTests(tests);
                practiceService.saveByPart(part);
            }
        } catch (Exception e) {
            if (Objects.nonNull(newTestID)) this.deleteTestById(newTestID);

            throw new TExamException(e);
        }
    }

    /**
     * Gets a list of all tests, paginated.
     *
     * @param page the page number
     * @param size the page size
     * @return a list of tests
     * @throws TExamException if an error occurs while getting the tests
     */
    @Override
    public List<TestDto> getTests(int page, int size) {
        try {
            List<Test> tests = this.findAll();
            long totalElement = tests.size();
            int truePage = TExamUtil.getCorrectPage(page, size, totalElement);
            int trueSize = TExamUtil.getCorrectSize(size, totalElement);

            return TExamUtil.paginateList(tests, PageRequest.of(truePage, trueSize))
                    .map(testMapper::convertModelToDto)
                    .toList();
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Gets a test by its ID.
     *
     * @param id the ID of the test
     * @return a test DTO
     * @throws NotFoundException if the test does not exist
     * @throws TExamException    if an error occurs while getting the test
     */
    @Override
    public TestDto getTestById(UUID id) {
        try {
            return this.findById(id).map(testMapper::convertModelToDto)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.TEST_E005));
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Gets a list of all tests for a given part.
     *
     * @param partId the ID of the part
     * @return a list of tests
     * @throws TExamException if an error occurs while getting the tests
     */
    @Override
    public List<TestDto> getTestsByPartId(UUID partId) {
        try {
            Part part = partService.findById(partId)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.PART_E002));

            return Optional.ofNullable(part.getTests()).orElse(Collections.emptyList()).stream()
                    .map(testMapper::convertModelToDto)
                    .toList();
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Updates a test.
     *
     * @param testReq the test request
     * @throws NotFoundException   if the test or the part does not exist
     * @throws BadRequestException if the type of the test is invalid
     */
    @Override
    public void updateTest(TestReq testReq) {
        try {
            Part part = partService.findById(testReq.getPartId())
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.PART_E002));

            part.getTests().stream()
                    .filter(test -> testReq.getId().equals(test.getId()))
                    .findFirst()
                    .map(test -> {
                        PracticeType practiceType = PracticeType.getType(testReq.getType());
                        if (Objects.equals(PracticeType.UNDEFINED, practiceType)) {
                            throw new BadRequestException(ExceptionConstant.TEST_E006);
                        }
                        List<Question> questions = questionService.updateQuestions(testReq.getQuestions());
                        List<UUID> questionsIds = questionService.getQuestionIDsByQuestions(questions);

                        test.setPartId(testReq.getPartId());
                        test.setType(practiceType);
                        test.setName(testReq.getName());
                        test.setQuestionIDs(questionsIds);
                        return test;
                    }).orElseThrow(() -> new NotFoundException(ExceptionConstant.TEST_E005));
            practiceService.saveByPart(part);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Deletes a test by its ID, and remove all questions of this test.
     *
     * @param id the ID of the test
     * @throws NotFoundException if the test or the part does not exist
     */
    @Override
    public void deleteTestById(UUID id) {
        try {
            Test test = this.findById(id)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.TEST_E005));
            Part part = partService.findById(test.getPartId())
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.PART_E002));

            part.getTests().removeIf(t -> {
                if (id.equals(t.getId())) {
                    if (Objects.nonNull(t.getQuestionIDs())) {
                        t.getQuestionIDs().parallelStream().forEach(questionService::deleteQuestionById);
                    }
                    return true;
                }
                return false;
            });
            practiceService.saveByPart(part);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Checks if a test with the given name exists in the given part.
     *
     * @param part     the part
     * @param testName the name of the test
     * @return true if the test exists, false otherwise
     */
    private boolean existsInPartByName(Part part, String testName) {
        return Objects.nonNull(part.getTests()) &&
                part.getTests().stream().anyMatch(test -> testName.equals(test.getName()));
    }

    /**
     * Finds all tests.
     *
     * @return a list of all tests
     */
    private List<Test> findAll() {
        return partService.findAll().parallelStream()
                .flatMap(part -> Optional.ofNullable(part.getTests()).orElse(Collections.emptyList()).stream())
                .toList();
    }

    /**
     * Finds a test by its ID.
     *
     * @param id the ID of the test
     * @return an optional containing the test, or an empty optional if the test does not exist
     */
    private Optional<Test> findById(UUID id) {
        return this.findAll().stream().filter(test -> id.equals(test.getId())).findFirst();
    }

    /**
     * Checks if a test with the given ID exists.
     *
     * @param id the ID of the test
     * @return true if the test exists, false otherwise
     */
    @Override
    public boolean existsById(UUID id) {
        return this.findAll().stream().anyMatch(test -> id.equals(test.getId()));
    }
}