package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.VocabularyCategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface VocabularyCategoryController {
    ResponseEntity<ResponseData> createVocabularyCategory(VocabularyCategoryDto vocabularyCategoryDto);
    ResponseEntity<ResponseData> getAllVocabularyCategories();
    ResponseEntity<ResponseData> getVocabularyCategoryByName(String name);
    ResponseEntity<ResponseData> updateVocabularyCategory(UUID vocabularyCategoryId,
                                                          VocabularyCategoryDto vocabularyCategoryDto);
    ResponseEntity<ResponseData> deleteVocabularyCategory(UUID vocabularyCategoryId);
}
