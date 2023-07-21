package com.tip.dg4.toeic_exam.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticePart {
    private String name;
    private String image;
    private String description;
    private List<PartLesson> partLessons;
    private List<PartTest> partTests;
}
