package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.ParamConstant;
import com.tip.dg4.toeic_exam.common.constants.SuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
import com.tip.dg4.toeic_exam.dto.VocabularyCategoryDto;
import com.tip.dg4.toeic_exam.services.VocabularyCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = ApiConstant.VOCABULARY_CATEGORY_API_ROOT)
public class VocabularyCategoryController {
    private final VocabularyCategoryService vocabularyCategoryService;

    public VocabularyCategoryController(VocabularyCategoryService vocabularyCategoryService) {
        this.vocabularyCategoryService = vocabularyCategoryService;
    }

    @PostMapping(path = ApiConstant.API_CREATE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<DataResponse> createVocabularyCategory(@RequestBody VocabularyCategoryDto vocabularyCategoryDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        vocabularyCategoryService.createVocabularyCategory(vocabularyCategoryDto);
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.VOCABULARY_CATEGORY_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = {ApiConstant.API_EMPTY, ApiConstant.API_SLASH},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getAllVocabularyCategories() {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.VOCABULARY_CATEGORY_S002,
                vocabularyCategoryService.getAllVocabularyCategories()
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = ApiConstant.API_EMPTY,
                params = ParamConstant.ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> getVocabularyCategoryById(@RequestParam(name = ParamConstant.ID) UUID categoryId) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.VOCABULARY_CATEGORY_S005,
                vocabularyCategoryService.getVocabularyCategoryById(categoryId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(path = ApiConstant.API_EMPTY,
                params = "name",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<DataResponse> getVocabularyCategoryByName(@RequestParam String name) {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.VOCABULARY_CATEGORY_S003,
                vocabularyCategoryService.getVocabularyCategoryByName(name)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(path = ApiConstant.API_EMPTY,
                params = "id",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<DataResponse> updateVocabularyCategory(@RequestParam(name = "id") UUID vocabularyCategoryId,
                                                                 @RequestBody VocabularyCategoryDto vocabularyCategoryDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        vocabularyCategoryService.updateVocabularyCategory(vocabularyCategoryId, vocabularyCategoryDto);
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.VOCABULARY_CATEGORY_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(path = ApiConstant.API_EMPTY,
                   params = "id",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<DataResponse> deleteVocabularyCategory(@RequestParam(name = "id") UUID vocabularyCategoryId) {
        HttpStatus httpStatus = HttpStatus.OK;
        vocabularyCategoryService.deleteVocabularyCategoryById(vocabularyCategoryId);
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                SuccessfulConstant.VOCABULARY_CATEGORY_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
