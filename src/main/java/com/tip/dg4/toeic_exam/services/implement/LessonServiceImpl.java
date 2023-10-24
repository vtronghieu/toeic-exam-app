package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.LessonDto;
import com.tip.dg4.toeic_exam.dto.requests.LessonReq;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.mappers.LessonMapper;
import com.tip.dg4.toeic_exam.models.Lesson;
import com.tip.dg4.toeic_exam.models.Part;
import com.tip.dg4.toeic_exam.services.LessonService;
import com.tip.dg4.toeic_exam.services.PartService;
import com.tip.dg4.toeic_exam.services.PracticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final PracticeService practiceService;
    private final PartService partService;
    private final LessonMapper lessonMapper;

    /**
     * Creates a new part lesson.
     *
     * @param lessonREQ the part lesson request DTO.
     * @throws NotFoundException if the part with the given ID does not exist.
     * @throws ConflictException if a part lesson with the given name already exists in the given part.
     * @throws TExamException    if any other exception occurs.
     */
    @Override
    public void createLesson(LessonReq lessonREQ) {
        try {
            Part part = partService.findById(lessonREQ.getPartId())
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PART_E002));
            if (existsInPartByName(lessonREQ.getPartId(), lessonREQ.getName())) {
                throw new ConflictException(TExamExceptionConstant.LESSON_E001);
            }
            Lesson newLesson = lessonMapper.convertReqToModel(lessonREQ);
            List<Lesson> lessons = Optional.ofNullable(part.getLessons()).orElse(new ArrayList<>());

            lessons.add(newLesson);
            part.setLessons(lessons);
            practiceService.saveByPart(part);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Gets a list of all part lessonDTOs.
     *
     * @return a list of all part lessonDTOs.
     * @throws TExamException if any exception occurs.
     */
    @Override
    public List<LessonDto> getLessons() {
        try {
            return lessonMapper.convertModelsToDTOs(this.findAll());
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Gets a list of part lesson DTOs for the given part.
     *
     * @param partId the ID of the part.
     * @return a list of part lesson DTOs for the given part.
     * @throws NotFoundException if the part with the given ID does not exist.
     * @throws TExamException    if any other exception occurs.
     */
    @Override
    public List<LessonDto> getLessonsByPartId(UUID partId) {
        try {
            Part part = partService.findById(partId)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PART_E002));

            return part.getLessons().stream().map(lessonMapper::convertModelDto).toList();
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Gets a part lesson DTO for the given part lesson ID.
     *
     * @param id the ID of the part lesson.
     * @return a part lesson DTO for the given part lesson ID.
     * @throws NotFoundException if the part lesson with the given ID does not exist.
     * @throws TExamException    if any other exception occurs.
     */
    @Override
    public LessonDto getLessonById(UUID id) {
        try {
            Lesson lesson = this.findById(id)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.LESSON_E002));

            return lessonMapper.convertModelDto(lesson);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Updates the part lesson with the given ID.
     *
     * @param lessonREQ the part lesson request DTO.
     * @throws NotFoundException if the part lesson with the given ID does not exist.
     * @throws NotFoundException if the part with the given ID does not exist.
     * @throws TExamException    if any other exception occurs.
     */
    @Override
    public void updateLessonById(LessonReq lessonREQ) {
        try {
            if (!existsById(lessonREQ.getId())) {
                throw new NotFoundException(TExamExceptionConstant.LESSON_E002);
            }
            Part part = partService.findById(lessonREQ.getPartId())
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PART_E002));

            part.getLessons().stream()
                    .filter(lesson -> lessonREQ.getId().equals(lesson.getId()))
                    .findFirst()
                    .map(lesson -> {
                        if (!Objects.equals(lessonREQ.getName(), lesson.getName()) &&
                                existsInPartByName(lessonREQ.getPartId(), lessonREQ.getName())) {
                            throw new ConflictException(TExamExceptionConstant.LESSON_E001);
                        }
                        lesson.setPartId(lessonREQ.getPartId());
                        lesson.setName(lessonREQ.getName());

                        return lesson;
                    }).orElseThrow(() -> new NotFoundException(TExamExceptionConstant.LESSON_E005));
            practiceService.saveByPart(part);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Deletes the part lesson with the given ID.
     *
     * @param id the ID of the part lesson.
     * @throws NotFoundException if the part lesson with the given ID does not exist.
     * @throws TExamException    if any other exception occurs.
     */
    @Override
    public void deleteLessonById(UUID id) {
        try {
            Lesson lesson = this.findById(id)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.LESSON_E002));

            partService.findById(lesson.getPartId())
                    .ifPresent(part -> {
                        part.getLessons().remove(lesson);
                        practiceService.saveByPart(part);
                    });
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Returns a list of all part lessons.
     *
     * @return a list of all part lessons.
     */
    @Override
    public List<Lesson> findAll() {
        return partService.findAll().stream()
                .flatMap(part -> Optional.ofNullable(part.getLessons()).orElse(Collections.emptyList()).stream())
                .toList();
    }

    /**
     * Finds a part lesson by its ID.
     *
     * @param id the ID of the part lesson to find.
     * @return an Optional containing the part lesson with the given ID, or empty if no part lesson with the given ID exists.
     */
    @Override
    public Optional<Lesson> findById(UUID id) {
        return this.findAll().stream().filter(lesson -> id.equals(lesson.getId())).findFirst();
    }

    /**
     * Checks if a part lesson with the given name exists in the given part.
     *
     * @param partId the ID of the part to check.
     * @param name   the name of the part lesson to check for.
     * @return true if a part lesson with the given name exists in the given part, false otherwise.
     */
    private boolean existsInPartByName(UUID partId, String name) {
        return this.findAll().stream()
                .filter(lesson -> partId.equals(lesson.getPartId()))
                .anyMatch(lesson -> name.equals(lesson.getName()));
    }

    /**
     * Checks if a part lesson with the given ID exists.
     *
     * @param id the ID of the part lesson to check for.
     * @return true if a part lesson with the given ID exists, false otherwise.
     */
    private boolean existsById(UUID id) {
        return this.findAll().stream().anyMatch(lesson -> id.equals(lesson.getId()));
    }
}
