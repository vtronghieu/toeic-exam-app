package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.dto.VocabularyCategoryDto;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.VocabularyCategoryMapper;
import com.tip.dg4.toeic_exam.models.VocabularyCategory;
import com.tip.dg4.toeic_exam.repositories.VocabularyCategoryRepository;
import com.tip.dg4.toeic_exam.services.VocabularyCategoryService;
import com.tip.dg4.toeic_exam.services.VocabularyService;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class VocabularyCategoryServiceImpl implements VocabularyCategoryService {
    private final VocabularyCategoryRepository vocabularyCategoryRepository;
    private final VocabularyService vocabularyService;
    private final VocabularyCategoryMapper vocabularyCategoryMapper;

    public VocabularyCategoryServiceImpl(VocabularyCategoryRepository vocabularyCategoryRepository,
                                         VocabularyService vocabularyService,
                                         VocabularyCategoryMapper vocabularyCategoryMapper) {
        this.vocabularyCategoryRepository = vocabularyCategoryRepository;
        this.vocabularyService = vocabularyService;
        this.vocabularyCategoryMapper = vocabularyCategoryMapper;
    }

    @Override
    public void createVocabularyCategory(VocabularyCategoryDto vocabularyCategoryDto) {
        String formatName = TExamUtil.toTitleCase(vocabularyCategoryDto.getName());
        if (vocabularyCategoryRepository.existsByName(formatName)) {
            throw new ConflictException(ExceptionConstant.VOCABULARY_CATEGORY_E001 + formatName);
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

    @Override
    public VocabularyCategoryDto getVocabularyCategoryById(UUID categoryId) {
        VocabularyCategory category = vocabularyCategoryRepository.findById(categoryId)
                                      .orElseThrow(() -> new NotFoundException(ExceptionConstant.VOCABULARY_CATEGORY_E002));

        return vocabularyCategoryMapper.convertModelToDto(category);
    }

    @Override
    public VocabularyCategoryDto getVocabularyCategoryByName(String name) {
        Optional<VocabularyCategory> optionalCategory = vocabularyCategoryRepository.findOneByNameIgnoreCase(name);
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException(ExceptionConstant.VOCABULARY_CATEGORY_E004 + name);
        }

        VocabularyCategory vocabularyCategory = optionalCategory.get();
        return vocabularyCategoryMapper.convertModelToDto(vocabularyCategory);
    }

    @Override
    public void updateVocabularyCategory(UUID vocabularyCategoryId, VocabularyCategoryDto vocabularyCategoryDto) {
        Optional<VocabularyCategory> optionalVocabularyCategory = vocabularyCategoryRepository.findById(vocabularyCategoryId);
        if (optionalVocabularyCategory.isEmpty()) {
            log.error(ExceptionConstant.VOCABULARY_CATEGORY_E003 + vocabularyCategoryId);
            throw new NotFoundException(ExceptionConstant.VOCABULARY_CATEGORY_E002);
        }

        VocabularyCategory vocabularyCategory = optionalVocabularyCategory.get();
        String formatName = TExamUtil.toTitleCase(vocabularyCategoryDto.getName());
        if (vocabularyCategory.isActive() == vocabularyCategoryDto.isActive() &&
            vocabularyCategoryRepository.existsByName(formatName)) {
            throw new ConflictException(ExceptionConstant.VOCABULARY_CATEGORY_E001 + formatName);
        }
        vocabularyCategory.setName(vocabularyCategoryDto.getName());
        vocabularyCategory.setActive(vocabularyCategoryDto.isActive());

        vocabularyCategoryRepository.save(vocabularyCategory);
    }

    @Override
    public void deleteVocabularyCategoryById(UUID categoryId) {
        if (!vocabularyCategoryRepository.existsById(categoryId)) {
            log.error(ExceptionConstant.VOCABULARY_CATEGORY_E003 + categoryId);
            throw new NotFoundException(ExceptionConstant.VOCABULARY_CATEGORY_E002);
        }
        vocabularyService.deleteCategoryIdFromCategoryIds(categoryId);
        vocabularyCategoryRepository.deleteById(categoryId);
    }
}
