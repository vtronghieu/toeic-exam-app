package com.tip.dg4.toeic_exam.services.implement;

import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.CreateSharedLinkWithSettingsErrorException;
import com.dropbox.core.v2.sharing.RequestedVisibility;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.exceptions.UnauthorizedException;
import com.tip.dg4.toeic_exam.services.UploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Log4j2
@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public List<String> uploadFile(List<MultipartFile> multipartFiles, String code, String uriPath) throws DbxException, IOException {

        List<String> images = new ArrayList<>();

        if (!code.isEmpty()) {
            setAccessToken(code, uriPath);
        }

        DbxRequestConfig config = DbxRequestConfig.newBuilder(TExamConstant.APP_DROPBOX).build();
        DbxClientV2 dbxClient = new DbxClientV2(config, this.readAccessTokenFromConfig());

        for (MultipartFile multipartFile : multipartFiles) {
            try (InputStream in = multipartFile.getInputStream()) {
                dbxClient.files().uploadBuilder(TExamConstant.SLASH + multipartFile.getOriginalFilename())
                        .withMode(WriteMode.ADD)
                        .withAutorename(true)
                        .withClientModified(new Date(System.currentTimeMillis()))
                        .uploadAndFinish(in);

                SharedLinkSettings.Builder sharedLinkSettingsBuilder = SharedLinkSettings.newBuilder().withRequestedVisibility(RequestedVisibility.PUBLIC);
                SharedLinkMetadata sharedLinkMetadata = dbxClient.sharing().createSharedLinkWithSettings(
                        TExamConstant.SLASH + multipartFile.getOriginalFilename(), sharedLinkSettingsBuilder.build());

                String image = sharedLinkMetadata.getUrl().replace(TExamConstant.IMAGE_DL, TExamConstant.IMAGE_RAW);
                images.add(image);

            } catch (InvalidAccessTokenException e) {
                throw new UnauthorizedException(ExceptionConstant.DROPBOX_E001);
            } catch (CreateSharedLinkWithSettingsErrorException e) {
                throw new BadRequestException(ExceptionConstant.DROPBOX_E002);
            } catch (Exception e) {
                throw new BadRequestException(ExceptionConstant.DROPBOX_E003);
            }
        }
        return images;
    }

    private String readAccessTokenFromConfig() {
        try (FileInputStream inputStream = new FileInputStream(TExamConstant.SOURCE_FILE_NAME)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty("dropbox.accessToken");
        } catch (IOException e) {
            throw new RuntimeException("Failed to read access token from config.", e);
        }

    }

    private void setAccessToken(String code, String uriPath) throws IOException, DbxException {
        FileInputStream inputStream = new FileInputStream(TExamConstant.SOURCE_FILE_NAME);
        Properties properties = new Properties();
        properties.load(inputStream);

        String APP_KEY = properties.getProperty("dropbox.appKey");
        String APP_SECRET = properties.getProperty("dropbox.appSecret");

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = DbxRequestConfig.newBuilder(TExamConstant.APP_DROPBOX).build();
        DbxWebAuth webAuth = new DbxWebAuth(config, appInfo);

        DbxAuthFinish authFinish = webAuth.finishFromCode(code, uriPath);
        String accessToken = authFinish.getAccessToken();

        properties.setProperty("dropbox.accessToken", accessToken);

        FileOutputStream outputStream = new FileOutputStream(TExamConstant.SOURCE_FILE_NAME);
        properties.store(outputStream, null);
        outputStream.close();
    }

    /**
     * Deletes a file from Dropbox.
     *
     * @param url The URL of the file to delete.
     * @throws TExamException If an error occurs while deleting the file.
     */
    public void deleteFile(String url) {
        try {
            DbxRequestConfig config = DbxRequestConfig.newBuilder(TExamConstant.APP_DROPBOX).build();
            DbxClientV2 dbxClient = new DbxClientV2(config, this.readAccessTokenFromConfig());
            String[] parts = url.split(TExamConstant.SLASH);
            String fileNameWithQuery = parts[parts.length - 1];
            String[] fileNameParts = fileNameWithQuery.split(TExamConstant.QUESTION_MARK_REGEX);
            String fileName = fileNameParts[0];

            dbxClient.files().deleteV2(TExamConstant.SLASH + fileName);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }
}
