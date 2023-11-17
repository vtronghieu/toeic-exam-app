package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.PartDto;
import com.tip.dg4.toeic_exam.dto.requests.PartReq;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.mappers.PartMapper;
import com.tip.dg4.toeic_exam.models.Part;
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.services.PartService;
import com.tip.dg4.toeic_exam.services.PracticeService;
import com.tip.dg4.toeic_exam.services.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class PartServiceImpl implements PartService {
    private final PracticeService practiceService;
    private final UploadService uploadService;
    private final PartMapper partMapper;

    /**
     * Creates a new practice part.
     *
     * @param partREQ the request object containing the practice part data
     * @throws NotFoundException if the practice with the given ID does not exist
     * @throws ConflictException if a practice part with the given name already exists in the practice
     * @throws TExamException    if any other error occurs
     */
    @Override
    public void createPart(PartReq partREQ) {
        try {
            Practice practice = practiceService.findById(partREQ.getPracticeId())
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));
            if (existByName(practice, partREQ.getName())) {
                throw new ConflictException(TExamExceptionConstant.PART_E001);
            }

            List<Part> parts = Optional.ofNullable(practice.getParts()).orElse(new ArrayList<>());

            parts.add(partMapper.convertReqToModel(partREQ));
            practice.setParts(parts);
            practiceService.save(practice);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Gets a list of practice parts for the given practice.
     *
     * @param practiceId the ID of the practice
     * @return a list of practice part DTOs
     * @throws NotFoundException if the practice with the given ID does not exist
     * @throws TExamException    if any other error occurs
     */
    @Override
    public List<PartDto> getPartsByPracticeId(UUID practiceId) {
        try {
            Practice practice = practiceService.findById(practiceId)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));

            return partMapper.convertModelsToDTOs(practice.getParts());
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Updates a practice part.
     *
     * @param partREQ the request object containing the updated practice part data
     * @throws NotFoundException if the practice or practice part with the given ID does not exist
     * @throws ConflictException if a practice part with the given name already exists in the practice
     * @throws TExamException    if any other error occurs
     */
    @Override
    public void updatePart(PartReq partREQ) {
        try {
            Practice practice = practiceService.findById(partREQ.getPracticeId())
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));

            practice.getParts().stream()
                    .filter(part -> Objects.nonNull(partREQ.getId()) && partREQ.getId().equals(part.getId()))
                    .findFirst()
                    .map(part -> {
                        if (!Objects.equals(partREQ.getName(), part.getName()) && existByName(practice, partREQ.getName())) {
                            throw new ConflictException(TExamExceptionConstant.PART_E001);
                        }
                        part.setPracticeId(partREQ.getPracticeId());
                        part.setName(partREQ.getName());
                        part.setImageURL(partREQ.getImageURL());
                        part.setDescription(partREQ.getDescription());

                        return part;
                    }).orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PART_E002));
            practiceService.save(practice);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Deletes a practice part by its ID.
     *
     * @param id the ID of the practice part to delete
     * @throws NotFoundException if the practice part with the given ID does not exist
     * @throws TExamException    if any other error occurs
     */
    @Override
    public void deletePartById(UUID id) {
        try {
            Part part = this.findById(id)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PART_E002));

            practiceService.findById(part.getPracticeId())
                    .ifPresent(practice -> {
                        if (practice.getParts().remove(part)) {
                            practiceService.save(practice);
                            this.deleteFile(part.getImageURL());
                        }
                    });
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Deletes the practice part with the given ID, from the practice with the given ID.
     *
     * @param practiceId the ID of the practice to delete the practice part from
     * @param partId     the ID of the practice part to delete
     * @throws TExamException if the practice part cannot be deleted
     */
    @Override
    public void deletePartByPracticeIdAndPartId(UUID practiceId, UUID partId) {
        try {
            Practice practice = practiceService.findById(practiceId)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));
            Part part = this.findByPracticeAndId(practice, partId)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PART_E002));

            if (practice.getParts().remove(part)) {
                practiceService.save(practice);
                this.deleteFile(part.getImageURL());
            }
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Finds all practice parts.
     *
     * @return a list of all practice parts
     */
    @Override
    public List<Part> findAll() {
        return practiceService.findAll().stream()
                .flatMap(practice -> Optional.ofNullable(practice.getParts()).orElse(Collections.emptyList()).stream())
                .toList();
    }

    /**
     * Finds a practice part by its ID.
     *
     * @param id the ID of the practice part to find
     * @return an optional containing the practice part, or empty if no practice part with the given ID was found
     */
    @Override
    public Optional<Part> findById(UUID id) {
        return this.findAll().stream().filter(part -> Objects.equals(id, part.getId())).findFirst();
    }

    /**
     * Finds a practice part by its ID, in the context of a given practice.
     *
     * @param practice the practice to search for the practice part in
     * @param id       the ID of the practice part to find
     * @return an optional containing the practice part, or empty if no practice part with the given ID was found in the given practice
     */
    @Override
    public Optional<Part> findByPracticeAndId(Practice practice, UUID id) {
        return Optional.ofNullable(practice.getParts()).orElse(Collections.emptyList())
                .stream().filter(part -> Objects.equals(id, part.getId())).findFirst();
    }

    /**
     * Deletes a file from the upload service.
     *
     * @param fileURL The URL of the file to delete.
     */
    private void deleteFile(String fileURL) {
        try {
            uploadService.deleteFile(fileURL);
        } catch (Exception ignored) {}
    }

    /**
     * Checks if a practice part with the given name exists.
     *
     * @param practice the practice to check
     * @param name     the name of the practice part to check
     * @return true if a practice part with the given name exists, false otherwise
     */
    private boolean existByName(Practice practice, String name) {
        return Objects.nonNull(practice.getParts()) &&
                practice.getParts().stream()
                        .anyMatch(part -> Objects.equals(name, part.getName()));
    }
}
