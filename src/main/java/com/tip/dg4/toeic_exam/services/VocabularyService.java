package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.VocabularyDto;

import java.util.List;
import java.util.UUID;

public interface VocabularyService {
    void createVocabulary(VocabularyDto vocabularyDto);
    List<VocabularyDto> getAllVocabularies();
    List<VocabularyDto> getVocabulariesByCategoryId(UUID categoryId);
    void updateVocabulary(UUID vocabularyId, VocabularyDto vocabularyDto);
    void deleteVocabularyById(UUID vocabularyId);
    void deleteCategoryIdFromCategoryIds(UUID categoryId);
    boolean existsById(UUID id);
}
