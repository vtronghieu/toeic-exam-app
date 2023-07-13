package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.VocabularyDto;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.VocabularyMapper;
import com.tip.dg4.toeic_exam.models.Vocabulary;
import com.tip.dg4.toeic_exam.repositories.VocabularyRepository;
import com.tip.dg4.toeic_exam.services.VocabularyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<VocabularyDto> getAllVocabularies() {
        return vocabularyRepository.findAll().stream()
                .map(vocabularyMapper::convertModelToDto)
                .toList();
    }

    @Override
    public List<VocabularyDto> getVocabulariesByCategoryId(UUID categoryId) {
        return vocabularyRepository.findByVocabularyCategoryIDsContaining(categoryId).stream()
                .map(vocabularyMapper::convertModelToDto)
                .toList();
    }

    @Override
    public void createVocabulary(VocabularyDto vocabularyDto) {
        Vocabulary vocabulary = vocabularyMapper.convertDtoToModel(vocabularyDto);
        vocabularyRepository.save(vocabulary);
    }

    @Override
    public void updateVocabulary(VocabularyDto vocabularyDto) {
        Vocabulary vocabulary = vocabularyRepository.findById(vocabularyDto.getId())
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.VOCABULARY_E001 + vocabularyDto.getWord()));
        vocabulary.setWord(vocabularyDto.getWord());
        vocabulary.setMean(vocabularyDto.getMean());
        vocabulary.setPronounce(vocabularyDto.getPronounce());
        vocabulary.setVocabularyCategoryIDs(vocabularyDto.getVocabularyCategoryIDs());
        vocabulary.setActive(vocabularyDto.isActive());
        vocabularyRepository.save(vocabulary);
    }

    @Override
    public void deleteVocabulary(UUID vocabularyId) {
        Vocabulary vocabulary = vocabularyRepository.findById(vocabularyId)
                .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.VOCABULARY_E002 + vocabularyId));
        vocabularyRepository.deleteById(vocabulary.getId());
    }

    @Override
    public void deleteVocabularyCategoryId(UUID vocabularyId, UUID categoryId) {
        Optional<Vocabulary> optionalVocabulary = vocabularyRepository.findById(vocabularyId);
        if (optionalVocabulary.isEmpty()) {
            log.error(TExamExceptionConstant.VOCABULARY_E002 + vocabularyId);
            throw new NotFoundException(TExamExceptionConstant.VOCABULARY_E003 + vocabularyId);
        }

        List<UUID> vocabularyCategoryIDs = optionalVocabulary.get().getVocabularyCategoryIDs();
        vocabularyCategoryIDs.removeIf(categoryId::equals);
        vocabularyRepository.save(optionalVocabulary.get());
    }
}
