package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.PartTestDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.PartTestMapper;
import com.tip.dg4.toeic_exam.models.PartTest;
import com.tip.dg4.toeic_exam.models.PracticeType;
import com.tip.dg4.toeic_exam.repositories.PartTestRepository;
import com.tip.dg4.toeic_exam.services.PartTestService;
import com.tip.dg4.toeic_exam.services.PracticePartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class PartTestServiceImpl implements PartTestService {
    private final PartTestRepository partTestRepository;
    private final PracticePartService practicePartService;
    private final PartTestMapper partTestMapper;

    public PartTestServiceImpl(PartTestRepository partTestRepository,
                               PracticePartService practicePartService,
                               PartTestMapper partTestMapper) {
        this.partTestRepository = partTestRepository;
        this.practicePartService = practicePartService;
        this.partTestMapper = partTestMapper;
    }

    @Override
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
    public List<PartTestDto> getPartTestsByPartId(UUID practicePartId) {
        if (!practicePartService.existsById(practicePartId)) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        List<PartTest> partTests = partTestRepository.findByPracticePartId(practicePartId);

        return partTests.stream().map(partTestMapper::convertModelToDto).toList();
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
        partTest.setPracticePartId(partTestDto.getPracticePartId());
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
    }
}