package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
<<<<<<< Updated upstream
import com.tip.dg4.toeic_exam.dto.PartLessonWithoutContentsDto;
=======
import com.tip.dg4.toeic_exam.dto.PartLessonDto;
>>>>>>> Stashed changes
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.PartLessonMapper;
import com.tip.dg4.toeic_exam.models.PartLesson;
<<<<<<< Updated upstream
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.models.PracticePart;
import com.tip.dg4.toeic_exam.repositories.PracticeRepository;
=======
import com.tip.dg4.toeic_exam.repositories.PartLessonRepository;
>>>>>>> Stashed changes
import com.tip.dg4.toeic_exam.services.PartLessonService;
import com.tip.dg4.toeic_exam.services.PracticePartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

<<<<<<< Updated upstream
import java.util.*;
=======
import java.util.List;
import java.util.Optional;
import java.util.UUID;
>>>>>>> Stashed changes

@Log4j2
@Service
public class PartLessonServiceImpl implements PartLessonService {
<<<<<<< Updated upstream
    private final PracticeRepository practiceRepository;
    private final PartLessonMapper partLessonMapper;

    public PartLessonServiceImpl(PracticeRepository practiceRepository,
                                 PartLessonMapper partLessonMapper) {
        this.practiceRepository = practiceRepository;
=======
    private final PartLessonRepository partLessonRepository;
    private final PracticePartService practicePartService;
    private final PartLessonMapper partLessonMapper;

    public PartLessonServiceImpl(PartLessonRepository partLessonRepository,
                                 PracticePartService practicePartService,
                                 PartLessonMapper partLessonMapper) {
        this.partLessonRepository = partLessonRepository;
        this.practicePartService = practicePartService;
>>>>>>> Stashed changes
        this.partLessonMapper = partLessonMapper;
    }

    @Override
<<<<<<< Updated upstream
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
=======
    public void createPartLesson(PartLessonDto partLessonDto) {
        UUID practicePartId = partLessonDto.getPracticePartId();
        if (!practicePartService.existsById(practicePartId)) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        if (partLessonRepository.existsByPracticePartIdAndName(practicePartId, partLessonDto.getName())) {
            throw new ConflictException(TExamExceptionConstant.PART_LESSON_E001);
        }
        PartLesson newPartLesson = partLessonMapper.convertDtoToModel(partLessonDto);

        partLessonRepository.save(newPartLesson);
    }

    @Override
    public List<PartLessonDto> getPartLessonsByPartId(UUID practicePartId) {
        if (!practicePartService.existsById(practicePartId)) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        List<PartLesson> partLessons = partLessonRepository.findByPracticePartId(practicePartId);

        return partLessons.stream().map(partLessonMapper::convertModelDto).toList();
    }

    @Override
    public void updatePartLesson(UUID partLessonId, PartLessonDto partLessonDto) {
        Optional<PartLesson> optionalPartLesson = partLessonRepository.findById(partLessonId);
        if (optionalPartLesson.isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.PART_LESSON_E002);
        }
        UUID practicePartId = partLessonDto.getPracticePartId();
        if (!practicePartService.existsById(practicePartId)) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        PartLesson partLesson = optionalPartLesson.get();
        if (!partLessonDto.getName().equals(partLesson.getName()) &&
                partLessonRepository.existsByPracticePartIdAndName(practicePartId, partLessonDto.getName())) {
            throw new ConflictException(TExamExceptionConstant.PART_LESSON_E001);
        }
        partLesson.setPracticePartId(partLessonDto.getPracticePartId());
        partLesson.setName(partLessonDto.getName());
        partLessonRepository.save(partLesson);
    }

    @Override
    public void deletePartLesson(UUID partLessonId) {
        if (!partLessonRepository.existsById(partLessonId)) {
            throw new NotFoundException(TExamExceptionConstant.PART_LESSON_E002);
        }
        partLessonRepository.deleteById(partLessonId);
>>>>>>> Stashed changes
    }
}
