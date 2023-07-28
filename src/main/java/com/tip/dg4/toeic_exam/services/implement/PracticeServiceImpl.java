package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.PracticeDto;
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
    public void createPractice(PracticeDto practiceDto) {
        if (practiceRepository.existsByNameIgnoreCase(practiceDto.getName())) {
            throw new ConflictException(TExamExceptionConstant.PRACTICE_E001);
        }
        if (Objects.isNull(PracticeType.getType(practiceDto.getType()))) {
            throw new BadRequestException(TExamExceptionConstant.PRACTICE_E002);
        }

        Practice practice = practiceMapper.convertDtoToModel(practiceDto);
        practiceRepository.save(practice);
    }

    @Override
    public List<PracticeDto> getAllPractices() {
        return practiceRepository.findAll().stream()
               .map(practiceMapper::convertModelToDto).toList();
    }

    @Override
    public void updatePractice(UUID practiceId, PracticeDto practiceDto) {
        Practice practice = practiceRepository.findById(practiceId)
                             .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.PRACTICE_E003));
        if (!Objects.equals(practiceDto.getName(), practice.getName()) &&
            practiceRepository.existsByNameIgnoreCase(practiceDto.getName())) {
            throw new ConflictException(TExamExceptionConstant.PRACTICE_E001);
        }
        PracticeType practiceDtoType = PracticeType.getType(practiceDto.getType());
        if (Objects.isNull(practiceDtoType)) {
            throw new BadRequestException(TExamExceptionConstant.PRACTICE_E002);
        }
        practice.setName(practiceDto.getName());
        practice.setType(practiceDtoType);
        practice.setImage(practiceDto.getImage());
        practiceRepository.save(practice);
    }

    @Override
    public void deletePractice(UUID practiceId) {
        if (!practiceRepository.existsById(practiceId)) {
            throw new NotFoundException(TExamExceptionConstant.PRACTICE_E003);
        }
        practiceRepository.deleteById(practiceId);
    }

    @Override
    public boolean existsById(UUID practiceId) {
        return practiceRepository.existsById(practiceId);
    }
}
