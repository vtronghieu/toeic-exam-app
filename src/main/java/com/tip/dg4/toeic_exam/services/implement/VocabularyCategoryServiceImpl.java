package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.VocabularyCategoryDto;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.mappers.VocabularyCategoryMapper;
import com.tip.dg4.toeic_exam.models.VocabularyCategory;
import com.tip.dg4.toeic_exam.repositories.VocabularyCategoryRepository;
import com.tip.dg4.toeic_exam.services.VocabularyCategoryService;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class VocabularyCategoryServiceImpl implements VocabularyCategoryService {
    private final VocabularyCategoryRepository vocabularyCategoryRepository;
    private final VocabularyCategoryMapper vocabularyCategoryMapper;

    public VocabularyCategoryServiceImpl(VocabularyCategoryRepository vocabularyCategoryRepository,
                                         VocabularyCategoryMapper vocabularyCategoryMapper) {
        this.vocabularyCategoryRepository = vocabularyCategoryRepository;
        this.vocabularyCategoryMapper = vocabularyCategoryMapper;
    }

    @Override
    public void createVocabularyCategory(VocabularyCategoryDto vocabularyCategoryDto) {
        String formatName = TExamUtil.toTitleCase(vocabularyCategoryDto.getName());
        if (vocabularyCategoryRepository.existsByName(formatName)) {
            throw new ConflictException(TExamExceptionConstant.VOCABULARY_CATEGORY_E001 + formatName);
        }

        vocabularyCategoryDto.setName(formatName);
        VocabularyCategory vocabularyCategory = vocabularyCategoryMapper.convertDtoToModel(vocabularyCategoryDto);
        vocabularyCategoryRepository.save(vocabularyCategory);
    }

    @Override
    public List<VocabularyCategoryDto> getAllVocabularyCategories() {
        return vocabularyCategoryRepository.findAll().stream()
                                           .map(vocabularyCategoryMapper::convertModelToDto)
                                           .toList();
    }
}
