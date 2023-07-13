package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.VocabularyQuestionDto;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.VocabularyQuestionMapper;
import com.tip.dg4.toeic_exam.models.VocabularyQuestion;
import com.tip.dg4.toeic_exam.repositories.VocabularyQuestionRepository;
import com.tip.dg4.toeic_exam.services.VocabularyQuestionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class VocabularyQuestionServiceImpl implements VocabularyQuestionService {
    private final VocabularyQuestionRepository vocabularyQuestionRepository;
    private final VocabularyQuestionMapper vocabularyQuestionMapper;


    public VocabularyQuestionServiceImpl(VocabularyQuestionRepository vocabularyQuestionRepository,
                                         VocabularyQuestionMapper vocabularyQuestionMapper) {
        this.vocabularyQuestionRepository = vocabularyQuestionRepository;
        this.vocabularyQuestionMapper = vocabularyQuestionMapper;
    }

    @Override
    public List<VocabularyQuestionDto> getAllQuestions() {
        return vocabularyQuestionRepository.findAll().stream()
                .map(vocabularyQuestionMapper::convertModelToDto)
                .toList();
    }

    @Override
    public List<VocabularyQuestionDto> getQuestionsByVocabularyIds(List<UUID> vocabularyIds) {
        List<VocabularyQuestion> vocabularyQuestions = new ArrayList<>();
        for (UUID vocabulary: vocabularyIds) {
            Optional<VocabularyQuestion> optionalVocabularyQuestion = vocabularyQuestionRepository.findOneByVocabularyId(vocabulary);
            optionalVocabularyQuestion.ifPresent(vocabularyQuestions::add);
        }
        return vocabularyQuestions.stream().map(vocabularyQuestionMapper::convertModelToDto).toList();
    }

    @Override
    public void createQuestion(VocabularyQuestionDto vocabularyQuestionDto) {
        vocabularyQuestionRepository.save(vocabularyQuestionMapper.convertDtoToModel(vocabularyQuestionDto));
    }


    @Override
    public void updateQuestion() {

    }

    @Override
    public void deleteQuestionById(UUID questionId) {
        VocabularyQuestion question = vocabularyQuestionRepository.findById(questionId).orElseThrow(() -> new NotFoundException(TExamExceptionConstant.VOCABULARY_QUESTION_E001));
        vocabularyQuestionRepository.delete(question);
    }
}
