package com.tip.dg4.toeic_exam.controllers.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.controllers.VocabularyCategoryController;
import com.tip.dg4.toeic_exam.dto.VocabularyCategoryDto;
import com.tip.dg4.toeic_exam.services.VocabularyCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VocabularyCategoryControllerImpl implements VocabularyCategoryController {
    private final VocabularyCategoryService vocabularyCategoryService;

    public VocabularyCategoryControllerImpl(VocabularyCategoryService vocabularyCategoryService) {
        this.vocabularyCategoryService = vocabularyCategoryService;
    }

    @Override
    public ResponseEntity<ResponseData> createVocabularyCategory(VocabularyCategoryDto vocabularyCategoryDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        vocabularyCategoryService.createVocabularyCategory(vocabularyCategoryDto);
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.VOCABULARY_CATEGORY_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

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
