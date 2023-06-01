package com.tip.dg4.toeic_exam.common.responses;

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
}
