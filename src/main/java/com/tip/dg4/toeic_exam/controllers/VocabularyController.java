package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.VocabularyDto;
import com.tip.dg4.toeic_exam.services.VocabularyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.VOCABULARY_API_ROOT)
public class VocabularyController {
    private final VocabularyService vocabularyService;

    public VocabularyController(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    @PostMapping(path = TExamApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> createVocabulary(@RequestBody VocabularyDto vocabularyDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        vocabularyService.createVocabulary(vocabularyDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_S002
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getAllVocabularies() {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_S001,
                vocabularyService.getAllVocabularies()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.VOCABULARY_API_GET_BY_CATEGORY_IDS,
                params = "categoryId",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getVocabulariesByCategoryId(@RequestParam UUID categoryId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_S005,
                vocabularyService.getVocabulariesByCategoryId(categoryId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = TExamApiConstant.API_EMPTY,
                params = "id",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> updateVocabulary(@RequestParam(name = "id") UUID vocabularyId,
                                                         @RequestBody VocabularyDto vocabularyDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        vocabularyService.updateVocabulary(vocabularyId, vocabularyDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(path = TExamApiConstant.API_EMPTY,
                   params = "id",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> deleteVocabularyById(@RequestParam(name = "id") UUID vocabularyId) {
        HttpStatus httpStatus = HttpStatus.OK;
        vocabularyService.deleteVocabularyById(vocabularyId);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
