package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.VocabularyCategoryDto;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.VocabularyCategoryMapper;
import com.tip.dg4.toeic_exam.mappers.VocabularyMapper;
import com.tip.dg4.toeic_exam.models.Vocabulary;
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
    private final VocabularyCategoryMapper vocabularyCategoryMapper;
    private final VocabularyMapper vocabularyMapper;
    private final VocabularyService vocabularyService;

    public VocabularyCategoryServiceImpl(VocabularyCategoryRepository vocabularyCategoryRepository,
                                         VocabularyCategoryMapper vocabularyCategoryMapper,
                                         VocabularyMapper vocabularyMapper,
                                         VocabularyService vocabularyService) {
        this.vocabularyCategoryRepository = vocabularyCategoryRepository;
        this.vocabularyCategoryMapper = vocabularyCategoryMapper;
        this.vocabularyMapper = vocabularyMapper;
        this.vocabularyService = vocabularyService;
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

    @Override
    public VocabularyCategoryDto getVocabularyCategoryByName(String name) {
        Optional<VocabularyCategory> optionalCategory = vocabularyCategoryRepository.findOneByNameIgnoreCase(name);
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.VOCABULARY_CATEGORY_E004 + name);
        }

        VocabularyCategory vocabularyCategory = optionalCategory.get();
        return vocabularyCategoryMapper.convertModelToDto(vocabularyCategory);
    }

    @Override
    public void updateVocabularyCategory(UUID vocabularyCategoryId, VocabularyCategoryDto vocabularyCategoryDto) {
        Optional<VocabularyCategory> optionalVocabularyCategory = vocabularyCategoryRepository.findById(vocabularyCategoryId);
        if (optionalVocabularyCategory.isEmpty()) {
            log.error(TExamExceptionConstant.VOCABULARY_CATEGORY_E003 + vocabularyCategoryId);
            throw new NotFoundException(TExamExceptionConstant.VOCABULARY_CATEGORY_E002);
        }

        VocabularyCategory vocabularyCategory = optionalVocabularyCategory.get();
        String formatName = TExamUtil.toTitleCase(vocabularyCategoryDto.getName());
        if (vocabularyCategory.isActive() == vocabularyCategoryDto.isActive() &&
            vocabularyCategoryRepository.existsByName(formatName)) {
            throw new ConflictException(TExamExceptionConstant.VOCABULARY_CATEGORY_E001 + formatName);
        }
        vocabularyCategory.setName(vocabularyCategoryDto.getName());
        vocabularyCategory.setActive(vocabularyCategoryDto.isActive());

        vocabularyCategoryRepository.save(vocabularyCategory);
    }

    @Override
    public void deleteVocabularyCategory(UUID vocabularyCategoryId) {
        if (!vocabularyCategoryRepository.existsById(vocabularyCategoryId)) {
            log.error(TExamExceptionConstant.VOCABULARY_CATEGORY_E003 + vocabularyCategoryId);
            throw new NotFoundException(TExamExceptionConstant.VOCABULARY_CATEGORY_E002);
        }

        List<Vocabulary> vocabularies = vocabularyService.getVocabulariesByCategoryId(vocabularyCategoryId).stream()
                                        .map(vocabularyMapper::convertDtoToModel).toList();
        for (Vocabulary vocabulary : vocabularies) {
            vocabularyService.deleteVocabularyCategoryId(vocabulary.getId(), vocabularyCategoryId);
        }
        vocabularyCategoryRepository.deleteById(vocabularyCategoryId);
    }
}
