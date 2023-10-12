package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.PracticePartDto;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.PracticePartMapper;
import com.tip.dg4.toeic_exam.models.PracticePart;
import com.tip.dg4.toeic_exam.repositories.PracticePartRepository;
import com.tip.dg4.toeic_exam.services.PracticePartService;
import com.tip.dg4.toeic_exam.services.PracticeService;
import com.tip.dg4.toeic_exam.services.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class PracticePartServiceImpl implements PracticePartService {
    private final PracticePartRepository practicePartRepository;
    private final PracticeService practiceService;
    private final UploadService uploadService;
    private final PracticePartMapper practicePartMapper;

    @Override
    public void createPracticePart(PracticePartDto practicePartDto) {
        UUID practiceId = practicePartDto.getPracticeId();
        if (!practiceService.existsById(practiceId)) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_E003);
        }
        if (practicePartRepository.existsByPracticeIdAndName(practicePartDto.getPracticeId(), practicePartDto.getName())) {
            throw new ConflictException(TExamExceptionConstant.PRACTICE_PART_E001);
        }
        PracticePart newPracticePart = practicePartMapper.convertDtoToModel(practicePartDto);
        newPracticePart.setPracticeId(practiceId);

        practicePartRepository.save(newPracticePart);
    }

    @Override
    public List<PracticePartDto> getPracticePartsByPracticeId(UUID practiceId) {
        if (!practiceService.existsById(practiceId)) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_E003);
        }
        List<PracticePart> practiceParts = practicePartRepository.findByPracticeId(practiceId);

        return practiceParts.stream().map(practicePartMapper::convertModelToDto).toList();
    }

    @Override
    public void updatePracticePart(UUID practicePartId, PracticePartDto practicePartDto) {
        Optional<PracticePart> optionalPracticePart = practicePartRepository.findById(practicePartId);
        if (optionalPracticePart.isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002);
        }
        if (!practiceService.existsById(practicePartDto.getPracticeId())) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_E003);
        }
        PracticePart practicePart = optionalPracticePart.get();
        if (!practicePartDto.getName().equals(practicePart.getName()) &&
                practicePartRepository.existsByPracticeIdAndName(practicePartDto.getPracticeId(), practicePartDto.getName())) {
            throw new ConflictException(TExamExceptionConstant.PRACTICE_PART_E001);
        }
        practicePart.setPracticeId(practicePartDto.getPracticeId());
        practicePart.setName(practicePartDto.getName());
        practicePart.setImage(practicePartDto.getImage());
        practicePart.setDescription(practicePartDto.getDescription());

        practicePartRepository.save(practicePart);
    }

    @Override
    public void deletePracticePartById(UUID practicePartId) {
        PracticePart practicePart = practicePartRepository.findById(practicePartId)
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_PART_E002));

        practicePartRepository.deleteById(practicePartId);
        uploadService.deleteFile(practicePart.getImage());
    }

    @Override
    public boolean existsById(UUID practicePartId) {
        return practicePartRepository.existsById(practicePartId);
    }
}
