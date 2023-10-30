package com.tip.dg4.toeic_exam.dto.requests;

import com.tip.dg4.toeic_exam.annotations.NotUUID;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class QuestionReq {
    private UUID id;

    @NotEmpty(message = TExamExceptionConstant.QUESTION_E002)
    @NotBlank(message = TExamExceptionConstant.QUESTION_E002)
    private String type;

    @NotUUID(message = TExamExceptionConstant.QUESTION_E008)
    private UUID objectTypeId;

    @NotEmpty(message = TExamExceptionConstant.QUESTION_E004)
    private String level;

    private List<String> imageURLs;

    private String audioURL;

    private String transcript;

    @Valid
    @NotEmpty(message = TExamExceptionConstant.QUESTION_DETAIL_E005)
    @Size(min = 1, message = TExamExceptionConstant.QUESTION_DETAIL_E005)
    private List<QuestionDetailReq> questionDetails;
}
