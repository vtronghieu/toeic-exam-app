package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.models.LessonContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartLessonDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID practicePartId;
    private String name;
    private List<LessonContent> lessonContents;
}
