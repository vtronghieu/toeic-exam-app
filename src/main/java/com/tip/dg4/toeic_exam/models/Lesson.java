package com.tip.dg4.toeic_exam.models;

import com.tip.dg4.toeic_exam.annotations.GenerateID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {
    @Id
    @GenerateID
    @Field(targetType = FieldType.STRING)
    private UUID id;

    @Field(targetType = FieldType.STRING)
    private UUID partId;

    private String name;

    private List<Content> contents;
}
