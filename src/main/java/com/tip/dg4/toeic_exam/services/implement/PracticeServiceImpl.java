package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.PracticeWithoutPartsDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.PracticeMapper;
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.models.PracticeType;
import com.tip.dg4.toeic_exam.repositories.PracticeRepository;
import com.tip.dg4.toeic_exam.services.PracticeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
public class PracticeServiceImpl implements PracticeService {
    private final PracticeRepository practiceRepository;
    private final PracticeMapper practiceMapper;

    public PracticeServiceImpl(PracticeRepository practiceRepository,
                               PracticeMapper practiceMapper) {
        this.practiceRepository = practiceRepository;
        this.practiceMapper = practiceMapper;
    }

    @Override
    public void createPracticeWithoutParts(PracticeWithoutPartsDto practiceWithoutPartsDto) {
        if (practiceRepository.existsByNameIgnoreCase(practiceWithoutPartsDto.getName())) {
            throw new ConflictException(TExamExceptionConstant.PRACTICE_E001);
        }
        if (Objects.isNull(PracticeType.getType(practiceWithoutPartsDto.getType()))) {
            throw new BadRequestException(TExamExceptionConstant.PRACTICE_E002);
        }

        Practice practice = practiceMapper.convertDtoWithoutPartsToModel(practiceWithoutPartsDto);
        practiceRepository.save(practice);
    }

    @Override
    public List<PracticeWithoutPartsDto> getAllPracticesWithoutParts() {
        return practiceRepository.findAll().stream()
               .map(practiceMapper::convertModelToDtoWithoutParts).toList();
    }

    @Override
    public void updatePracticeWithoutParts(UUID practiceId, PracticeWithoutPartsDto practiceWithoutPartsDto) {
        Practice practice = practiceRepository.findById(practiceId)
                             .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));
        if (!Objects.equals(practiceWithoutPartsDto.getName(), practice.getName()) &&
            practiceRepository.existsByNameIgnoreCase(practiceWithoutPartsDto.getName())) {
            throw new ConflictException(TExamExceptionConstant.PRACTICE_E001);
        }
        PracticeType practiceDtoType = PracticeType.getType(practiceWithoutPartsDto.getType());
        if (Objects.isNull(practiceDtoType)) {
            throw new BadRequestException(TExamExceptionConstant.PRACTICE_E002);
        }
        practice.setName(practiceWithoutPartsDto.getName());
        practice.setType(practiceDtoType);
        practice.setImage(practiceWithoutPartsDto.getImage());
        practiceRepository.save(practice);
    }

    @Override
    public void deletePracticeWithoutParts(UUID practiceId) {
        if (!practiceRepository.existsById(practiceId)) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_E003);
        }
        practiceRepository.deleteById(practiceId);
    }
}
