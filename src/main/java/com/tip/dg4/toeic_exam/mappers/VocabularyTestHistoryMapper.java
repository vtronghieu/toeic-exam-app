package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.VocabularyTestHistoryDto;
import com.tip.dg4.toeic_exam.models.VocabularyTestHistory;
import org.springframework.stereotype.Component;

@Component
public class VocabularyTestHistoryMapper {
    public VocabularyTestHistory convertDtoToModel(VocabularyTestHistoryDto vocabularyTestHistoryDto) {
        VocabularyTestHistory vocabularyTestHistory = new VocabularyTestHistory();

        vocabularyTestHistory.setId(vocabularyTestHistoryDto.getId());
        vocabularyTestHistory.setVocabularyAnswers(vocabularyTestHistoryDto.getVocabularyAnswers());
        vocabularyTestHistory.setUserId(vocabularyTestHistoryDto.getUserId());

        return vocabularyTestHistory;
    }
    public VocabularyTestHistoryDto convertModelToDto(VocabularyTestHistory vocabularyTestHistory) {
        VocabularyTestHistoryDto vocabularyTestHistoryDto = new VocabularyTestHistoryDto();

        vocabularyTestHistoryDto.setId(vocabularyTestHistory.getId());
        vocabularyTestHistoryDto.setVocabularyAnswers(vocabularyTestHistory.getVocabularyAnswers());
        vocabularyTestHistoryDto.setUserId(vocabularyTestHistory.getUserId());

        return vocabularyTestHistoryDto;
    }
}
