package com.tip.dg4.toeic_exam.common.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse {
    private int code;
    private String status;
    private String message;
    private Object data;

    public DataResponse(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = Collections.emptyMap();
    }
}
