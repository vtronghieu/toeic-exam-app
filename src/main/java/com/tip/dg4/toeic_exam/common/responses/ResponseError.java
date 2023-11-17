package com.tip.dg4.toeic_exam.common.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TExamConstant.DATE_TIME_FORMAT)
    private LocalDateTime timestamp;
    private int code;
    private String status;
    private Object message;
}
