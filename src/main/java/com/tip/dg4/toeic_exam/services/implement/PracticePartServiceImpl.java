package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.PracticePartWithoutLessonsAndTestsDto;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.PracticePartMapper;
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.models.PracticePart;
import com.tip.dg4.toeic_exam.repositories.PracticeRepository;
import com.tip.dg4.toeic_exam.services.PracticePartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
public class PracticePartServiceImpl implements PracticePartService {
    private final PracticeRepository practiceRepository;
    private final PracticePartMapper practicePartMapper;

    public PracticePartServiceImpl(PracticeRepository practiceRepository,
                                   PracticePartMapper practicePartMapper) {
        this.practiceRepository = practiceRepository;
        this.practicePartMapper = practicePartMapper;
    }

    @Override
    public void createPartWithoutLessonsAndTests(UUID practiceId,
                                                 PracticePartWithoutLessonsAndTestsDto partWithoutLessonsAndTestsDto) {
        Practice practice = practiceRepository.findById(practiceId)
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));
        List<PracticePart> practiceParts = Objects.isNull(practice.getPracticeParts()) ? new ArrayList<>() : practice.getPracticeParts();
        boolean existsPartByName = practiceParts.stream()
                .anyMatch(practicePart -> partWithoutLessonsAndTestsDto.getName().equalsIgnoreCase(practicePart.getName()));
        if (existsPartByName) {
            throw new ConflictException(TExamExceptionConstant.PRACTICE_PART_E001 + partWithoutLessonsAndTestsDto.getName());
        }
        PracticePart newPracticePart = practicePartMapper.convertDtoWithoutLessonsAndTestsToModel(partWithoutLessonsAndTestsDto);
        practiceParts.add(newPracticePart);
        practice.setPracticeParts(practiceParts);
        practiceRepository.save(practice);
    }

    @Override
    public List<PracticePartWithoutLessonsAndTestsDto> getPartsWithoutLessonsAndTestsByPracticeId(UUID practiceId) {
        Practice practice = practiceRepository.findById(practiceId)
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002));

        List<PracticePart> practiceParts = Objects.nonNull(practice.getPracticeParts()) ?
                                           practice.getPracticeParts() :
                                           new ArrayList<>();
        List<PracticePartWithoutLessonsAndTestsDto> partsWithoutLessonsAndTests = new ArrayList<>();
        if (!practiceParts.isEmpty()) {
            partsWithoutLessonsAndTests = practiceParts.stream()
                                          .map(practicePartMapper::convertModelDtoWithoutLessonsAndTests)
                                          .toList();
        }

        return partsWithoutLessonsAndTests;
    }
}
