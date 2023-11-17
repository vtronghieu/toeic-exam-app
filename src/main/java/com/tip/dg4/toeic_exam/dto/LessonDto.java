package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID partId;

    private String name;

    private List<ContentDto> contents;
}
