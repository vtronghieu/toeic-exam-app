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
public class PracticeDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;

    private String name;

    private String type;

    private String imageURL;

    private List<PartDto> parts;
}
