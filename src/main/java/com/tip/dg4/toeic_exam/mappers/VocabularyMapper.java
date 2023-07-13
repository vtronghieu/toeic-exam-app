package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.VocabularyDto;
import com.tip.dg4.toeic_exam.models.Vocabulary;
import org.springframework.stereotype.Component;

@Component
public class VocabularyMapper {
    public Vocabulary convertDtoToModel(VocabularyDto vocabularyDto) {
        Vocabulary vocabulary = new Vocabulary();

        vocabulary.setId(vocabularyDto.getId());
        vocabulary.setWord(vocabularyDto.getWord());
        vocabulary.setMean(vocabularyDto.getMean());
        vocabulary.setPronounce(vocabularyDto.getPronounce());
        vocabulary.setActive(vocabularyDto.isActive());
        vocabulary.setVocabularyCategoryIDs(vocabularyDto.getVocabularyCategoryIDs());

        return vocabulary;
    }

    public VocabularyDto convertModelToDto(Vocabulary vocabulary) {
        VocabularyDto vocabularyDto = new VocabularyDto();

        vocabularyDto.setId(vocabulary.getId());
        vocabularyDto.setWord(vocabulary.getWord());
        vocabularyDto.setMean(vocabulary.getMean());
        vocabularyDto.setPronounce(vocabulary.getPronounce());
        vocabularyDto.setActive(vocabulary.isActive());
        vocabularyDto.setVocabularyCategoryIDs(vocabulary.getVocabularyCategoryIDs());

        return vocabularyDto;
    }
}