package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamParamConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.dto.requests.PartReq;
import com.tip.dg4.toeic_exam.services.PartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = TExamApiConstant.PART_API_ROOT)
@RequiredArgsConstructor
public class PartController {
    private final PartService partService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> createPart(@RequestBody @Valid PartReq partREQ) {
        partService.createPart(partREQ);

        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_S001
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = TExamParamConstant.PRACTICE_ID,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData> getPartsByPracticeId(@RequestParam(TExamParamConstant.PRACTICE_ID) UUID practiceId) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_S002,
                partService.getPartsByPracticeId(practiceId)
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> updatePart(@RequestBody @Valid PartReq partREQ) {
        partService.updatePart(partREQ);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_S003
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = TExamParamConstant.ID,
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> deletePartById(@RequestParam(TExamParamConstant.ID) UUID id) {
        partService.deletePartById(id);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }

    @DeleteMapping(params = {TExamParamConstant.PRACTICE_ID, TExamParamConstant.PART_ID},
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(TExamConstant.ADMIN_AUTHORIZED)
    public ResponseEntity<ResponseData> deletePartByPracticeIdAndPartId(@RequestParam(TExamParamConstant.PRACTICE_ID) UUID practiceId,
                                                                        @RequestParam(TExamParamConstant.PART_ID) UUID partId) {
        partService.deletePartByPracticeIdAndPartId(practiceId, partId);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseData result = new ResponseData(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                TExamSuccessfulConstant.PART_S004
        );

        return new ResponseEntity<>(result, httpStatus);
    }
}
