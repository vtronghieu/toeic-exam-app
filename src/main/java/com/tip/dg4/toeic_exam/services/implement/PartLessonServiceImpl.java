package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.PartLessonDto;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.PartLessonMapper;
import com.tip.dg4.toeic_exam.models.PartLesson;
import com.tip.dg4.toeic_exam.repositories.PartLessonRepository;
import com.tip.dg4.toeic_exam.services.PartLessonService;
import com.tip.dg4.toeic_exam.services.PracticePartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class PartLessonServiceImpl implements PartLessonService {
    private final PartLessonRepository partLessonRepository;
    private final PracticePartService practicePartService;
    private final PartLessonMapper partLessonMapper;

    public PartLessonServiceImpl(PartLessonRepository partLessonRepository,
                                 PracticePartService practicePartService,
                                 PartLessonMapper partLessonMapper) {
        this.partLessonRepository = partLessonRepository;
        this.practicePartService = practicePartService;
        this.partLessonMapper = partLessonMapper;
    }

    @Override
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
        partLesson.setLessonContents(partLessonDto.getLessonContents());
        partLessonRepository.save(partLesson);
    }

    @Override
    public void deletePartLesson(UUID partLessonId) {
        if (!partLessonRepository.existsById(partLessonId)) {
            throw new NotFoundException(TExamExceptionConstant.PART_LESSON_E002);
        }
        partLessonRepository.deleteById(partLessonId);
    }

    @Override
    public PartLessonDto getPartLessonsById(UUID practicePartId, UUID partLessonId) {
        if (!practicePartService.existsById(practicePartId)) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        Optional<PartLesson> optionalPartLesson = partLessonRepository.findById(partLessonId);
        if (optionalPartLesson.isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.PART_LESSON_E002);
        }
        return partLessonMapper.convertModelDto(optionalPartLesson.get());
    }
}
