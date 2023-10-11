package com.tip.dg4.toeic_exam.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TExamConstant {
    public static final String EMPTY = "";
    public static final String SLASH = "/";
    public static final String ASTERISK = "*";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final String ACCESS_TOKEN = "access-token";
    public static final int ORDER_TO_INDEX_CONVERTING_FACTOR = 1;
    public static final String IMAGE_DL = "dl=0";
    public static final String IMAGE_RAW = "raw=1";
    public static final String SOURCE_FILE_NAME = "src/main/resources/application.properties";
    public static final String APP_DROPBOX = "ToeicDG4";
    public static final String ADMIN_AUTHORIZED = "hasAuthority('Admin')";
    public static final String DOT_REGEX = "\\.";
    public static final String DTO_REGEX = "\\D[Tt][Oo]";
    public static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    public static final String PHONE_REGEX = "^(?:\\+?\\d{1,3})?[-.\\s]?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}(?:\\s?x\\d+)?$";
}
