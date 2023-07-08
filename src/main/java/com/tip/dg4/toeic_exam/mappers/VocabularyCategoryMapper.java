package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.VocabularyCategoryDto;
import com.tip.dg4.toeic_exam.models.VocabularyCategory;
import org.springframework.stereotype.Component;

@Component
public class VocabularyCategoryMapper {
    public VocabularyCategory convertDtoToModel(VocabularyCategoryDto vocabularyCategoryDto) {
        VocabularyCategory vocabularyCategory = new VocabularyCategory();

        vocabularyCategory.setId(vocabularyCategoryDto.getId());
        vocabularyCategory.setName(vocabularyCategoryDto.getName());
        vocabularyCategory.setActive(vocabularyCategoryDto.isActive());

        return vocabularyCategory;
    }

    public VocabularyCategoryDto convertModelToDto(VocabularyCategory vocabularyCategory) {
        VocabularyCategoryDto vocabularyCategoryDto = new VocabularyCategoryDto();

        vocabularyCategoryDto.setId(vocabularyCategory.getId());
        vocabularyCategoryDto.setName(vocabularyCategory.getName());
        vocabularyCategoryDto.setActive(vocabularyCategoryDto.isActive());

        return vocabularyCategoryDto;
    }
}
