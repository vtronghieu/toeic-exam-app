package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class VocabularyDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    private String word;
    private String pronounce;
    private String mean;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<UUID> categoryIds;
    @JsonProperty(value = "isActive")
    private boolean isActive;
}
