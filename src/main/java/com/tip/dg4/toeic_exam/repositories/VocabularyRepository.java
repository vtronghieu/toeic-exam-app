package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.Vocabulary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface VocabularyRepository extends MongoRepository<Vocabulary, UUID> {
    List<Vocabulary> findByVocabularyCategoryIDsContaining(UUID vocabularyCategoryID);
}
