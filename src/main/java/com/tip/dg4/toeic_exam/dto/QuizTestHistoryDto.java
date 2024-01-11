package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.dto.question.UserAnswerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class QuizTestHistoryDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID categoryId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String type;
    private List<UserAnswerDto> userAnswers;
}
