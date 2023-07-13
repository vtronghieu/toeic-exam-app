package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.VocabularyTestHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface VocabularyTestHistoryRepository extends MongoRepository<VocabularyTestHistory, UUID> {
}
