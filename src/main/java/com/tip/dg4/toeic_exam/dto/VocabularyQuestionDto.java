package com.tip.dg4.toeic_exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyQuestionDto {
    private UUID id;
    private String question;
    private List<String> answers;
    private String correctAnswer;
    private UUID vocabularyId;
}