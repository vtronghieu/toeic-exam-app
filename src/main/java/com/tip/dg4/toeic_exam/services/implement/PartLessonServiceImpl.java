package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.PartLessonWithoutContentsDto;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.PartLessonMapper;
import com.tip.dg4.toeic_exam.models.PartLesson;
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.models.PracticePart;
import com.tip.dg4.toeic_exam.repositories.PracticeRepository;
import com.tip.dg4.toeic_exam.services.PartLessonService;
import com.tip.dg4.toeic_exam.services.PracticePartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class PartLessonServiceImpl implements PartLessonService {
    private final PracticeRepository practiceRepository;
    private final PartLessonMapper partLessonMapper;

    public PartLessonServiceImpl(PracticeRepository practiceRepository,
                                 PartLessonMapper partLessonMapper) {
        this.practiceRepository = practiceRepository;
        this.partLessonMapper = partLessonMapper;
    }

    @Override
    public void createLessonWithoutContents(UUID practiceId, 
                                            UUID practicePartId, 
                                            PartLessonWithoutContentsDto partLessonWithoutContentsDto) {
        Practice practice = practiceRepository.findById(practiceId)
                            .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));
        List<PracticePart> practiceParts = Optional.ofNullable(practice.getPracticeParts()).orElse(new ArrayList<>());
        PracticePart practicePart = practiceParts.stream().filter(part -> practicePartId.equals(part.getId())).findFirst()
                                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002));
        List<PartLesson> partLessons = Optional.ofNullable(practicePart.getPartLessons()).orElse(new ArrayList<>());
        int partIndex = 0;
        if (!partLessons.isEmpty()) {
            if (this.existsByName(practicePart, partLessonWithoutContentsDto.getName())) {
                throw new ConflictException(TExamExceptionConstant.PART_LESSON_E001);
            }
            partIndex = practiceParts.indexOf(practicePart);
        }
        PartLesson newPartLesson = partLessonMapper.convertDtoWithoutContentsToModel(partLessonWithoutContentsDto);
        partLessons.add(newPartLesson);
        practicePart.setPartLessons(partLessons);
        practiceParts.set(partIndex, practicePart);
        practice.setPracticeParts(practiceParts);
        practiceRepository.save(practice);
    }

    @Override
    public List<PartLessonWithoutContentsDto> getLessonsWithoutContentsByPartId(UUID practicePartId) {
        List<Practice> practices = practiceRepository.findAll();
        List<PracticePart> practiceParts = new ArrayList<>();
        for (Practice practice : practices) {
            if (Objects.nonNull(practice.getPracticeParts())) {
                practiceParts.addAll(practice.getPracticeParts());
            }
        }
        boolean existsPartById = practiceParts.stream().anyMatch(practicePart -> practicePartId.equals(practicePart.getId()));
        if (!existsPartById) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        List<PartLesson> partLessons = new ArrayList<>();
        for (PracticePart practicePart : practiceParts) {
            if (Objects.nonNull(practicePart.getPartLessons()) && practicePartId.equals(practicePart.getId())) {
                partLessons.addAll(practicePart.getPartLessons());
            }
        }

        return partLessons.stream().map(partLessonMapper::convertModelDtoWithoutContents).toList();
    }

    private boolean existsByName(PracticePart practicePart, String name) {
        return practicePart.getPartLessons().stream().anyMatch(partLesson -> name.equals(partLesson.getName()));
    }
}
