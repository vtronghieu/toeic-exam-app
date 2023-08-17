package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    private String type;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID objectTypeId;
    private String level;
    private String audioQuestion;
    private String transcript;
    private List<MultipartFile> images;
    private List<ChildQuestionDto> questions;
}
