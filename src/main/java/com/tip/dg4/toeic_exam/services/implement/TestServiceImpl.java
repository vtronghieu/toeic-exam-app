package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.mappers.TestMapper;
import com.tip.dg4.toeic_exam.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final PartService partService;
    private final TestMapper testMapper;
    private final TestHistoryService testHistoryService;
    private final QuestionService questionService;
    private final QuestionDetailService questionDetailService;

    /*@Override
    public void createPartTest(PartTestDto partTestDto) {
        if (!practicePartService.existsById(partTestDto.getPracticePartId())) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        PracticeType practiceType = PracticeType.getType(partTestDto.getType());
        if (Objects.isNull(practiceType)) {
            throw new BadRequestException(TExamExceptionConstant.PART_TEST_E002);
        }
        if (partTestRepository.existsByPracticePartIdAndName(partTestDto.getPracticePartId(), partTestDto.getName())) {
            throw new ConflictException(TExamExceptionConstant.PART_TEST_E001);
        }
        PartTest newPartTest = partTestMapper.convertDtoToModel(partTestDto);

        partTestRepository.save(newPartTest);
    }

    @Override
    public List<PartTestDto> getPartTestsByPartId(UUID practicePartId, UUID userId) {
        if (!practicePartService.existsById(practicePartId)) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        List<PartTest> partTests = partTestRepository.findByPracticePartId(practicePartId);
        List<PartTestDto> testDtoList = partTests.stream().map(partTestMapper::convertModelToDto).collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        testDtoList.parallelStream().forEach(testDto -> {
            List<TestHistory> testHistories = testHistoryService.getTestHistoriesByTestIdAndUserId(userId, testDto.getId());

            if (!testHistories.isEmpty()) {
                int lastIndex = testHistories.size() - TExamConstant.ORDER_TO_INDEX_CONVERTING_FACTOR;
                List<QuestionDto> questions = questionService.getQuestionsByObjectTypeId(testHistories.get(lastIndex).getTestId());

                int totalQuestions = questions.parallelStream()
                        .mapToInt(quest -> {
                            List<QuestionDetail> questionDetails = questionDetailService.findByQuestionId(quest.getId());
                            return questionDetails.size();
                        })
                        .sum();

                int count = (int) testHistories.get(lastIndex).getUserAnswers().parallelStream()
                        .filter(UserAnswer::isCorrect)
                        .count();

                testDto.setCorrectAnswer(count);
                testDto.setTotalQuestions(totalQuestions);
            }
        });
        return testDtoList;
    }

    public void updatePartTest(UUID partTestId, PartTestDto partTestDto) {
        Optional<PartTest> optionalPartTest = partTestRepository.findById(partTestId);
        if (optionalPartTest.isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.PART_TEST_E003);
        }
        if (!practicePartService.existsById(partTestDto.getPracticePartId())) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        PracticeType practiceType = PracticeType.getType(partTestDto.getType());
        if (Objects.isNull(practiceType)) {
            throw new BadRequestException(TExamExceptionConstant.PART_TEST_E002);
        }
        PartTest partTest = optionalPartTest.get();
        if (!partTestDto.getName().equals(partTest.getName()) &&
                partTestRepository.existsByPracticePartIdAndName(partTestDto.getPracticePartId(), partTestDto.getName())){
            throw new ConflictException(TExamExceptionConstant.PART_TEST_E001);
        }
        partTest.setPartId(partTestDto.getPracticePartId());
        partTest.setName(partTestDto.getName());
        partTest.setType(practiceType);

        partTestRepository.save(partTest);
    }

    @Override
    public void deletePartTestById(UUID partTestId) {
        if (!partTestRepository.existsById(partTestId)) {
            throw new NotFoundException(TExamExceptionConstant.PART_TEST_E003);
        }

        partTestRepository.deleteById(partTestId);
    }

    @Override
    public boolean existsById(UUID partTestId) {
        return partTestRepository.existsById(partTestId);
    }*/
}