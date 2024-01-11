package com.tip.dg4.toeic_exam.dto.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
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
public class QuestionDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;

    @NotEmpty(message = ExceptionConstant.QUESTION_E002)
    @NotBlank(message = ExceptionConstant.QUESTION_E002)
    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID objectTypeId;

    @NotEmpty(message = ExceptionConstant.QUESTION_E004)
    private String level;

    private List<String> imageURLs;

    private String audioURL;

    private String transcript;

    @Valid
    @NotEmpty(message = ExceptionConstant.QUESTION_DETAIL_E005)
    @Size(min = 1, message = ExceptionConstant.QUESTION_DETAIL_E005)
    private List<QuestionDetailDto> questionDetails;
}
