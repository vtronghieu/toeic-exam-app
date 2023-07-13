package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.VocabularyDto;

import java.util.List;
import java.util.UUID;

public interface VocabularyService {
    List<VocabularyDto> getAllVocabularies();
    List<VocabularyDto> getVocabulariesByCategoryId(UUID categoryId);
    void createVocabulary(VocabularyDto vocabularyDto);
    void updateVocabulary(VocabularyDto vocabularyDto);
    void deleteVocabulary(UUID vocabularyId);
    void deleteVocabularyCategoryId(UUID vocabularyId, UUID categoryId);
}
