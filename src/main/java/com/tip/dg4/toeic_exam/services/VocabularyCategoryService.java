package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.VocabularyCategoryDto;

import java.util.List;
import java.util.UUID;

public interface VocabularyCategoryService {
    void createVocabularyCategory(VocabularyCategoryDto vocabularyCategoryDto);
    List<VocabularyCategoryDto> getAllVocabularyCategories();
    VocabularyCategoryDto getVocabularyCategoryByName(String name);
    void updateVocabularyCategory(UUID vocabularyCategoryId, VocabularyCategoryDto vocabularyCategoryDto);
    void deleteVocabularyCategory(UUID vocabularyCategoryId);
}
