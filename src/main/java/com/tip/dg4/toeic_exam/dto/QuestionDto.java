package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.models.OptionAnswers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    private String type;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID objectTypeId;
    private String level;
    private String textQuestion;
    private String audioQuestion;
    private List<String> images;
    private OptionAnswers optionAnswers;
}
