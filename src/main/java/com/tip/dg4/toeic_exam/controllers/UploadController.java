package com.tip.dg4.toeic_exam.controllers;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.responses.DataResponse;
import com.tip.dg4.toeic_exam.services.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = ApiConstant.UPLOAD_API_ROOT)
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }


    @PostMapping(path = "/uploadFile",
            params = {"code","uriPath"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataResponse> uploadImage(@RequestBody List<MultipartFile> files,
                                                    @RequestParam("code") String code,
                                                    @RequestParam("uriPath") String uriPath) throws Exception {
        HttpStatus httpStatus = HttpStatus.OK;
        DataResponse result = new DataResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                "oki",
                uploadService.uploadFile(files, code,uriPath)
        );
        return new ResponseEntity<>(result, httpStatus);

    }


}
