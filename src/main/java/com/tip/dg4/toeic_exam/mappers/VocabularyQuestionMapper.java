package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.VocabularyQuestionDto;
import com.tip.dg4.toeic_exam.models.VocabularyQuestion;
import org.springframework.stereotype.Component;

@Component
public class VocabularyQuestionMapper {
    public VocabularyQuestion convertDtoToModel(VocabularyQuestionDto vocabularyQuestionDto) {
        VocabularyQuestion question = new VocabularyQuestion();

        question.setId(vocabularyQuestionDto.getId());
        question.setQuestion(vocabularyQuestionDto.getQuestion());
        question.setAnswers(vocabularyQuestionDto.getAnswers());
        question.setCorrectAnswer(vocabularyQuestionDto.getCorrectAnswer());
        question.setVocabularyId(vocabularyQuestionDto.getVocabularyId());

        return question;
    }
    public VocabularyQuestionDto convertModelToDto(VocabularyQuestion question) {
        VocabularyQuestionDto vocabularyDto = new VocabularyQuestionDto();

        vocabularyDto.setId(question.getId());
        vocabularyDto.setQuestion(question.getQuestion());
        vocabularyDto.setAnswers(question.getAnswers());
        vocabularyDto.setCorrectAnswer(question.getCorrectAnswer());
        vocabularyDto.setVocabularyId(question.getVocabularyId());

        return vocabularyDto;
    }
}
