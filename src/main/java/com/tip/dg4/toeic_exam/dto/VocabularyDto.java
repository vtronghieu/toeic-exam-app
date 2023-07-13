package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyDto {
    @Id
    @Field(targetType = FieldType.STRING)
    private UUID id;
    private String word;
    private String mean;
    private String pronounce;
    @JsonProperty(value = "isActive")
    private boolean isActive;
    @Field(targetType = FieldType.STRING)
    private List<UUID> vocabularyCategoryIDs;
}
