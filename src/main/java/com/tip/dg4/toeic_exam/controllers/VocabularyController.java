package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
import com.tip.dg4.toeic_exam.dto.VocabularyDto;
import com.tip.dg4.toeic_exam.services.VocabularyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = ApiConstant.VOCABULARY_API_ROOT)
public class VocabularyController {
    private final VocabularyService vocabularyService;

    public VocabularyController(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    @PostMapping(path = ApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<DataResponse> createVocabulary(@RequestBody VocabularyDto vocabularyDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        vocabularyService.createVocabulary(vocabularyDto);
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.VOCABULARY_S002
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = {ApiConstant.API_EMPTY, ApiConstant.API_SLASH},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getAllVocabularies() {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.VOCABULARY_S001,
                vocabularyService.getAllVocabularies()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = ApiConstant.VOCABULARY_API_GET_BY_CATEGORY_IDS,
                params = "categoryId",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getVocabulariesByCategoryId(@RequestParam UUID categoryId) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.VOCABULARY_S005,
                vocabularyService.getVocabulariesByCategoryId(categoryId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = ApiConstant.API_EMPTY,
                params = "id",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<DataResponse> updateVocabulary(@RequestParam(name = "id") UUID vocabularyId,
                                                         @RequestBody VocabularyDto vocabularyDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        vocabularyService.updateVocabulary(vocabularyId, vocabularyDto);
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.VOCABULARY_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(path = ApiConstant.API_EMPTY,
                   params = "id",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<DataResponse> deleteVocabularyById(@RequestParam(name = "id") UUID vocabularyId) {
        HttpStatus httpStatus = HttpStatus.OK;
        vocabularyService.deleteVocabularyById(vocabularyId);
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.VOCABULARY_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
