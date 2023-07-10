package com.tip.dg4.toeic_exam.controllers.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.controllers.VocabularyCategoryController;
import com.tip.dg4.toeic_exam.dto.VocabularyCategoryDto;
import com.tip.dg4.toeic_exam.services.VocabularyCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = TExamApiConstant.VOCABULARY_CATEGORY_API_ROOT)
public class VocabularyCategoryControllerImpl implements VocabularyCategoryController {
    private final VocabularyCategoryService vocabularyCategoryService;

    public VocabularyCategoryControllerImpl(VocabularyCategoryService vocabularyCategoryService) {
        this.vocabularyCategoryService = vocabularyCategoryService;
    }

    @Override
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

    @Override
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
}
