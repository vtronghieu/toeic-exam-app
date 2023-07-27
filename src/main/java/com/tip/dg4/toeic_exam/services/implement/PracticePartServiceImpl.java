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

import java.util.*;

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
        List<PracticePart> practiceParts = Optional.ofNullable(practice.getPracticeParts()).orElse(new ArrayList<>());
        boolean existsPartByName = practiceParts.stream()
                .anyMatch(practicePart -> partWithoutLessonsAndTestsDto.getName().equalsIgnoreCase(practicePart.getName()));
        if (existsPartByName) {
            throw new ConflictException(TExamExceptionConstant.PRACTICE_PART_E001);
        }
        PracticePart newPracticePart = practicePartMapper.convertDtoWithoutLessonsAndTestsToModel(partWithoutLessonsAndTestsDto);
        newPracticePart.setId(UUID.randomUUID());
        practiceParts.add(newPracticePart);
        practice.setPracticeParts(practiceParts);
        practiceRepository.save(practice);
    }

    @Override
    public List<PracticePartWithoutLessonsAndTestsDto> getPartsWithoutLessonsAndTestsByPracticeId(UUID practiceId) {
        Practice practice = practiceRepository.findById(practiceId)
                            .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002));
        List<PracticePart> practiceParts = Optional.ofNullable(practice.getPracticeParts()).orElse(new ArrayList<>());
        List<PracticePartWithoutLessonsAndTestsDto> partsWithoutLessonsAndTests = new ArrayList<>();
        if (!practiceParts.isEmpty()) {
            partsWithoutLessonsAndTests = practiceParts.stream()
                                          .map(practicePartMapper::convertModelDtoWithoutLessonsAndTests)
                                          .toList();
        }

        return partsWithoutLessonsAndTests;
    }

    @Override
    public void updatePartWithoutLessonsAndTests(UUID practiceId,
                                                 UUID practicePartId,
                                                 PracticePartWithoutLessonsAndTestsDto partWithoutLessonsAndTestsDto) {
        Practice practice = practiceRepository.findById(practiceId)
                            .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));
        List<PracticePart> practiceParts = Optional.ofNullable(practice.getPracticeParts()).orElse(new ArrayList<>());
        PracticePart practicePart = new PracticePart();
        int partIndex = 0;
        if (!practiceParts.isEmpty()) {
            practicePart = this.findById(practice, practiceId)
                           .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002));
            boolean existsByName = !partWithoutLessonsAndTestsDto.getName().equals(practicePart.getName()) &&
                                   this.existsByName(practice, partWithoutLessonsAndTestsDto.getName());
            if (existsByName) {
                throw new ConflictException(TExamExceptionConstant.PRACTICE_E001);
            }
            partIndex = practiceParts.indexOf(practicePart);
        }
        practicePart.setName(partWithoutLessonsAndTestsDto.getName());
        practicePart.setImage(partWithoutLessonsAndTestsDto.getImage());
        practicePart.setDescription(partWithoutLessonsAndTestsDto.getDescription());

        practiceParts.set(partIndex, practicePart);
        practice.setPracticeParts(practiceParts);
        practiceRepository.save(practice);
    }

    @Override
    public void deletePartById(UUID practiceId, UUID practicePartId) {
        Practice practice = practiceRepository.findById(practiceId)
                            .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));
        List<PracticePart> practiceParts = practice.getPracticeParts();
        if (findById(practice, practicePartId).isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        practiceParts.removeIf(practicePart -> practicePartId.equals(practicePart.getId()));
        practiceRepository.save(practice);
    }

    @Override
    public List<PracticePart> getAllPracticeParts() {
        List<Practice> practices = practiceRepository.findAll();
        List<PracticePart> practiceParts = new ArrayList<>();

        for (Practice practice : practices) {
            if (Objects.nonNull(practice.getPracticeParts())) {
                practiceParts.addAll(practice.getPracticeParts());
            }
        }

        return practiceParts;
    }

    private Optional<PracticePart> findById(Practice practice, UUID id) {
        return practice.getPracticeParts().stream().filter(practicePart -> id.equals(practicePart.getId())).findFirst();
    }

    private boolean existsByName(Practice practice, String name) {
        return practice.getPracticeParts().stream().anyMatch(practicePart -> name.equals(practicePart.getName()));
    }
}
