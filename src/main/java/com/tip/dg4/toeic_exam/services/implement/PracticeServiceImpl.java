package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.PracticeDto;
import com.tip.dg4.toeic_exam.dto.requests.PracticeReq;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.mappers.PracticeMapper;
import com.tip.dg4.toeic_exam.models.Lesson;
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.models.Part;
import com.tip.dg4.toeic_exam.models.enums.PracticeType;
import com.tip.dg4.toeic_exam.repositories.PracticeRepository;
import com.tip.dg4.toeic_exam.services.PartService;
import com.tip.dg4.toeic_exam.services.PracticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PracticeServiceImpl implements PracticeService {
    private final PracticeRepository practiceRepository;
    private final PracticeMapper practiceMapper;
    @Lazy
    private final PartService partService;

    /**
     * Creates a new practice.
     *
     * @param practiceReq The practice request DTO.
     * @throws ConflictException   If the practice name already exists.
     * @throws BadRequestException If the practice type is invalid.
     * @throws TExamException      If an error occurs while creating the practice.
     */
    @Override
    public void createPractice(PracticeReq practiceReq) {
        try {
            if (practiceRepository.existsByName(practiceReq.getName())) {
                throw new ConflictException(TExamExceptionConstant.PRACTICE_E001);
            }
            if (Objects.equals(PracticeType.UNDEFINED, PracticeType.getType(practiceReq.getType()))) {
                throw new BadRequestException(TExamExceptionConstant.PRACTICE_E006);
            }

            Practice practice = practiceMapper.convertReqToModel(practiceReq);
            practiceRepository.save(practice);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Gets all practices.
     *
     * @return A list of practice REQs.
     * @throws TExamException If an error occurs while getting the practices.
     */
    @Override
    public List<PracticeReq> getAllPractices() {
        try {
            return practiceRepository.findAll().stream()
                    .map(practiceMapper::convertModelToReq).toList();
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Gets the practice with the given ID.
     *
     * @param id The practice ID.
     * @return A practice DTO, or `null` if the practice does not exist.
     * @throws NotFoundException If the practice does not exist.
     * @throws TExamException    If an error occurs while getting the practice.
     */
    @Override
    public PracticeDto getPracticeById(UUID id) {
        try {
            return practiceRepository.findById(id)
                    .map(practiceMapper::convertModelToDto)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Updates the practice with the given ID.
     *
     * @param id          The practice ID.
     * @param practiceReq The practice request DTO.
     * @throws NotFoundException   If the practice does not exist.
     * @throws BadRequestException If the practice name already exists or the practice type is invalid.
     * @throws TExamException      If an error occurs while updating the practice.
     */
    @Override
    public void updatePractice(UUID id, PracticeReq practiceReq) {
        try {
            Practice practice = practiceRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));
            if (practiceRepository.existsByName(practiceReq.getName()) &&
                    !Objects.equals(practiceReq.getName(), practice.getName())) {
                throw new BadRequestException(TExamExceptionConstant.PRACTICE_E001);
            }
            PracticeType practiceType = PracticeType.getType(practiceReq.getType());
            if (Objects.equals(PracticeType.UNDEFINED, practiceType)) {
                throw new BadRequestException(TExamExceptionConstant.PRACTICE_E006);
            }

            practice.setName(practiceReq.getName());
            practice.setType(practiceType);
            practice.setImageURL(practiceReq.getImageURL());
            practiceRepository.save(practice);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Deletes the practice with the given ID.
     *
     * @param id The practice ID.
     * @throws NotFoundException If the practice does not exist.
     * @throws TExamException    If an error occurs while deleting the practice.
     */
    @Override
    public void deletePractice(UUID id) {
        try {
            if (!practiceRepository.existsById(id)) {
                throw new NotFoundException(TExamExceptionConstant.PRACTICE_E003);
            }
            practiceRepository.deleteById(id);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Checks if a practice exists with the given ID.
     *
     * @param practiceId The practice ID.
     * @return `true` if the practice exists, `false` otherwise.
     */
    @Override
    public boolean existsById(UUID practiceId) {
        return practiceRepository.existsById(practiceId);
    }

    /**
     * Finds a practice by its ID.
     *
     * @param id The ID of the practice to find.
     * @return An Optional of the practice, or empty if no practice with the given ID exists.
     */
    @Override
    public Optional<Practice> findById(UUID id) {
        return practiceRepository.findById(id);
    }

    /**
     * Finds all practices.
     *
     * @return A list of all practices.
     */
    @Override
    public List<Practice> findAll() {
        return practiceRepository.findAll();
    }

    /**
     * Saves a practice.
     *
     * @param practice The practice to save.
     * @return The saved practice.
     */
    @Override
    public Practice save(Practice practice) {
        return practiceRepository.save(practice);
    }

    /**
     * Saves a practice part.
     *
     * @param part The practice part to save.
     */
    @Override
    public void saveByPart(Part part) {
        Practice practice = practiceRepository.findById(part.getPracticeId())
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));

        practice.getParts().stream()
                .filter(localPart -> part.getId().equals(localPart.getId()))
                .findFirst()
                .map(localPart -> {
                    localPart.setPracticeId(part.getPracticeId());
                    localPart.setName(part.getName());
                    localPart.setImageURL(part.getImageURL());
                    localPart.setDescription(part.getDescription());
                    localPart.setLessons(part.getLessons());
                    localPart.setTests(part.getTests());

                    return localPart;
                }).orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PART_E002));
        practiceRepository.save(practice);
    }

    /**
     * Saves a part lesson.
     *
     * @param lesson The part lesson to save.
     */
    @Override
    public void saveByLesson(Lesson lesson) {
        Part part = partService.findById(lesson.getPartId())
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PART_E002));

        part.getLessons().stream()
                .filter(localLesson -> lesson.getId().equals(localLesson.getId()))
                .findFirst()
                .map(localLesson -> {
                    localLesson.setPartId(lesson.getPartId());
                    localLesson.setName(lesson.getName());
                    localLesson.setContents(lesson.getContents());

                    return localLesson;
                }).orElseThrow(() -> new NotFoundException(TExamExceptionConstant.LESSON_E002));
        this.saveByPart(part);
    }
}
