package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.VocabularyQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface VocabularyQuestionRepository extends MongoRepository<VocabularyQuestion, UUID> {
    Optional<VocabularyQuestion> findOneByVocabularyId(UUID vocabularyId);
}
