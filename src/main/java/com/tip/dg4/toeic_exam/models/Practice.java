package com.tip.dg4.toeic_exam.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "practices")
public class Practice {
    @Id
    @Field(targetType = FieldType.STRING)
    private UUID id;
    private String name;
    private PracticeType type;
    private String image;
    private List<PracticePart> practiceParts;
}
