package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.VocabularyDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.VocabularyMapper;
import com.tip.dg4.toeic_exam.models.Vocabulary;
import com.tip.dg4.toeic_exam.repositories.VocabularyRepository;
import com.tip.dg4.toeic_exam.services.VocabularyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class VocabularyServiceImpl implements VocabularyService {
    private final VocabularyRepository vocabularyRepository;
    private final VocabularyMapper vocabularyMapper;

    public VocabularyServiceImpl(VocabularyRepository vocabularyRepository,
                                 VocabularyMapper vocabularyMapper) {
        this.vocabularyRepository = vocabularyRepository;
        this.vocabularyMapper = vocabularyMapper;
    }

    @Override
    public void createVocabulary(VocabularyDto vocabularyDto) {
        if (vocabularyRepository.existsByWord(vocabularyDto.getWord())) {
            throw new BadRequestException(TExamExceptionConstant.VOCABULARY_E004 + vocabularyDto.getWord());
        }

        Vocabulary vocabulary = vocabularyMapper.convertDtoToModel(vocabularyDto);
        vocabularyRepository.save(vocabulary);
    }

    @Override
    public List<VocabularyDto> getAllVocabularies() {
        return vocabularyRepository.findAll().stream()
               .map(vocabularyMapper::convertModelToDto).toList();
    }

    @Override
    public List<VocabularyDto> getVocabulariesByCategoryId(UUID categoryId) {
        List<Vocabulary> vocabularies = vocabularyRepository.findByCategoryIds(categoryId);
        if (vocabularies.isEmpty()) {
            log.error(TExamExceptionConstant.VOCABULARY_E005 + categoryId);
            throw new NotFoundException(TExamExceptionConstant.VOCABULARY_E003);
        }

        return vocabularies.stream().map(vocabularyMapper::convertModelToDto).toList();
    }

    @Override
    public void updateVocabulary(UUID vocabularyId, VocabularyDto vocabularyDto) {
        Optional<Vocabulary> optionalVocabulary = vocabularyRepository.findById(vocabularyId);
        if (optionalVocabulary.isEmpty()) {
            log.error(TExamExceptionConstant.VOCABULARY_E002 + vocabularyId);
            throw new NotFoundException(TExamExceptionConstant.VOCABULARY_E003);
        }
        Vocabulary vocabulary = optionalVocabulary.get();
        if (!Objects.equals(vocabulary.getWord(), vocabularyDto.getWord()) &&
            vocabularyRepository.existsByWord(vocabularyDto.getWord())) {
            throw new BadRequestException(TExamExceptionConstant.VOCABULARY_E004 + vocabularyDto.getWord());
        }
        vocabulary.setWord(vocabularyDto.getWord());
        vocabulary.setPronounce(vocabularyDto.getPronounce());
        vocabulary.setMean(vocabularyDto.getMean());
        vocabulary.setCategoryIds(vocabularyDto.getCategoryIds());
        vocabulary.setActive(vocabularyDto.isActive());

        vocabularyRepository.save(vocabulary);
    }

    @Override
    public void deleteVocabularyById(UUID vocabularyId) {
        if (!vocabularyRepository.existsById(vocabularyId)) {
            log.error(TExamExceptionConstant.VOCABULARY_E002 + vocabularyId);
            throw new NotFoundException(TExamExceptionConstant.VOCABULARY_E003);
        }

        vocabularyRepository.deleteById(vocabularyId);
    }

    @Override
    public void deleteCategoryIdFromCategoryIds(UUID categoryId) {
        List<Vocabulary> vocabularies = vocabularyRepository.findByCategoryIds(categoryId);
        if (!vocabularies.isEmpty()) {
            for (Vocabulary vocabulary : vocabularies) {
                List<UUID> categoryIdsFromVocabulary = vocabulary.getCategoryIds();
                categoryIdsFromVocabulary.remove(categoryId);
                if (categoryIdsFromVocabulary.isEmpty()) {
                    vocabularyRepository.delete(vocabulary);
                } else {
                    vocabulary.setCategoryIds(categoryIdsFromVocabulary);
                    vocabularyRepository.save(vocabulary);
                }
            }
        }
    }

    @Override
    public boolean existsById(UUID id) {
        return vocabularyRepository.existsById(id);
    }
}
