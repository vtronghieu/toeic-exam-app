package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.VocabularyCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface VocabularyCategoryRepository extends MongoRepository<VocabularyCategory, UUID> {
    boolean existsByName(String name);
    Optional<VocabularyCategory> findOneByNameIgnoreCase(String name);
}
