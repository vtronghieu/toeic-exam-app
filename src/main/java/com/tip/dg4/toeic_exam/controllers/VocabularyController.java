package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.VocabularyDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface VocabularyController {
    ResponseEntity<ResponseData> getAllVocabulary();
    ResponseEntity<ResponseData> getVocabulariesByCategoryId(UUID categoryId);
    ResponseEntity<ResponseData> createVocabulary(VocabularyDto vocabularyDto);
    ResponseEntity<ResponseData> updateVocabulary(VocabularyDto vocabularyDto);
    ResponseEntity<ResponseData> deleteVocabulary(UUID vocabularyId);
}
