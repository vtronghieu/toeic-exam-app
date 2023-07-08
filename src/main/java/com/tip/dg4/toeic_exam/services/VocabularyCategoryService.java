package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.VocabularyCategoryDto;

import java.util.List;

public interface VocabularyCategoryService {
    void createVocabularyCategory(VocabularyCategoryDto vocabularyCategoryDto);
    List<VocabularyCategoryDto> getAllVocabularyCategories();
}
