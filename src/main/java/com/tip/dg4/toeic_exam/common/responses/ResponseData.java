package com.tip.dg4.toeic_exam.common.responses;

import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private int code;
    private String status;
    private String message;
    private Object data;

    public ResponseData(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = TExamConstant.EMPTY;
    }
}
