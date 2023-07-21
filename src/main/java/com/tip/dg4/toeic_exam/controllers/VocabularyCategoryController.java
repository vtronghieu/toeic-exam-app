package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.VocabularyCategoryDto;
import com.tip.dg4.toeic_exam.services.VocabularyCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.VOCABULARY_CATEGORY_API_ROOT)
public class VocabularyCategoryController {
    private final VocabularyCategoryService vocabularyCategoryService;

    public VocabularyCategoryController(VocabularyCategoryService vocabularyCategoryService) {
        this.vocabularyCategoryService = vocabularyCategoryService;
    }

    @PostMapping(path = TExamApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> createVocabularyCategory(@RequestBody VocabularyCategoryDto vocabularyCategoryDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        vocabularyCategoryService.createVocabularyCategory(vocabularyCategoryDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_CATEGORY_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getAllVocabularyCategories() {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_CATEGORY_S002,
                vocabularyCategoryService.getAllVocabularyCategories()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = TExamApiConstant.API_EMPTY,
                params = "name",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> getVocabularyCategoryByName(@RequestParam String name) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_CATEGORY_S003,
                vocabularyCategoryService.getVocabularyCategoryByName(name)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = TExamApiConstant.API_EMPTY,
                params = "id",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> updateVocabularyCategory(@RequestParam(name = "id") UUID vocabularyCategoryId,
                                                                 @RequestBody VocabularyCategoryDto vocabularyCategoryDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        vocabularyCategoryService.updateVocabularyCategory(vocabularyCategoryId, vocabularyCategoryDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_CATEGORY_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(path = TExamApiConstant.API_EMPTY,
                   params = "id",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResponseData> deleteVocabularyCategory(@RequestParam(name = "id") UUID vocabularyCategoryId) {
        HttpStatus httpStatus = HttpStatus.OK;
        vocabularyCategoryService.deleteVocabularyCategoryById(vocabularyCategoryId);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_CATEGORY_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
