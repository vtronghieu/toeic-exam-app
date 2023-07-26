package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.PartTestWithoutUserAnswerAndFinishTimeDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.PartTestMapper;
import com.tip.dg4.toeic_exam.models.PartTest;
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.models.PracticePart;
import com.tip.dg4.toeic_exam.models.PracticeType;
import com.tip.dg4.toeic_exam.repositories.PracticeRepository;
import com.tip.dg4.toeic_exam.services.PartTestService;
import com.tip.dg4.toeic_exam.services.PracticePartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class PartTestServiceImpl implements PartTestService {
    private final PracticeRepository practiceRepository;
    private final PracticePartService practicePartService;
    private final PartTestMapper partTestMapper;

    public PartTestServiceImpl(PracticeRepository practiceRepository,
                               PracticePartService practicePartService,
                               PartTestMapper partTestMapper) {
        this.practiceRepository = practiceRepository;
        this.practicePartService = practicePartService;
        this.partTestMapper = partTestMapper;
    }

    @Override
    public void createTest(UUID practiceId, UUID partId, PartTestWithoutUserAnswerAndFinishTimeDto testWithoutUserAnswerAndFinishTimeDto) {
        Practice practice = practiceRepository.findById(practiceId)
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));
        List<PracticePart> practiceParts = Optional.ofNullable(practice.getPracticeParts()).orElse(new ArrayList<>());
        PracticePart practicePart = practiceParts.stream().filter(part -> partId.equals(part.getId())).findFirst()
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002));
        List<PartTest> partTests = Optional.ofNullable(practicePart.getPartTests()).orElse(new ArrayList<>());
        int partIndex = 0;
        if (!partTests.isEmpty()) {
            PracticeType practiceType = PracticeType.getType(testWithoutUserAnswerAndFinishTimeDto.getType());
            if (Objects.isNull(practiceType) || !practiceType.equals(practice.getType())) {
                throw new BadRequestException(TExamExceptionConstant.PART_TEST_E002);
            }
            if (this.existsByName(practicePart, testWithoutUserAnswerAndFinishTimeDto.getName())) {
                throw new ConflictException(TExamExceptionConstant.PART_TEST_E001);
            }
            partIndex = practiceParts.indexOf(practicePart);
        }
        PartTest newPartTest = partTestMapper.convertDtoWithoutUserAnswerAndFinishTimeToModel(testWithoutUserAnswerAndFinishTimeDto);
        partTests.add(newPartTest);
        practicePart.setPartTests(partTests);
        practiceParts.set(partIndex, practicePart);
        practice.setPracticeParts(practiceParts);
        practiceRepository.save(practice);
    }

    @Override
    public List<PartTestWithoutUserAnswerAndFinishTimeDto> getTestsByPartId(UUID partId) {
        List<PracticePart> practiceParts = practicePartService.getAllPracticeParts();
        List<PartTest> partTests = new ArrayList<>();
        boolean existsPartById = practiceParts.stream().anyMatch(practicePart -> partId.equals(practicePart.getId()));
        if (!existsPartById) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        for (PracticePart practicePart : practiceParts) {
            if (Objects.nonNull(practicePart.getPartTests()) && partId.equals(practicePart.getId())) {
                partTests.addAll(practicePart.getPartTests());
            }
        }

        return partTests.stream().map(partTestMapper::convertModelToDtoWithoutUserAnswerAndFinishTime).toList();
    }

    private boolean existsByName(PracticePart practicePart, String name) {
        return practicePart.getPartTests().stream().anyMatch(partTest -> name.equals(partTest.getName()));
    }
}