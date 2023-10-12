package com.tip.dg4.toeic_exam.services;

import com.dropbox.core.DbxException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadService {
    List<String> uploadFile(List<MultipartFile> multipartFiles, String code, String uriPath) throws DbxException, IOException;

    void deleteFile(String url);
}
