package com.tip.dg4.toeic_exam.controllers.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.controllers.VocabularyController;
import com.tip.dg4.toeic_exam.dto.VocabularyDto;
import com.tip.dg4.toeic_exam.services.VocabularyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping(path = TExamApiConstant.VOCABULARY_ROOT)
public class VocabularyControllerImpl implements VocabularyController {
    private final VocabularyService vocabularyService;

    public VocabularyControllerImpl(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    @GetMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ResponseData> getAllVocabulary() {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                            httpStatus.value(),
                            httpStatus.getReasonPhrase(),
                            TExamSuccessfulConstant.VOCABULARY_S001,
                            vocabularyService.getAllVocabularies()
        );
        return new ResponseEntity<>(result, httpStatus);
    }
    @GetMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                params = "categoryId" ,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
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
    @PostMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
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
    @PutMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ResponseData> updateVocabulary(@RequestBody VocabularyDto vocabularyDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        vocabularyService.updateVocabulary(vocabularyDto);
        ResponseData result = new ResponseData(
                            httpStatus.value(),
                            httpStatus.getReasonPhrase(),
                            TExamSuccessfulConstant.VOCABULARY_S003
        );
        return new ResponseEntity<>(result, httpStatus);
    }
    @DeleteMapping(path = {TExamApiConstant.API_EMPTY, TExamApiConstant.API_SLASH},
            params = "vocabularyId" ,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<ResponseData> deleteVocabulary(@RequestParam UUID vocabularyId) {
        vocabularyService.deleteVocabulary(vocabularyId);
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                            httpStatus.value(),
                            httpStatus.getReasonPhrase(),
                            TExamSuccessfulConstant.VOCABULARY_S004
        );
        return new ResponseEntity<>(result, httpStatus);
    }
}
