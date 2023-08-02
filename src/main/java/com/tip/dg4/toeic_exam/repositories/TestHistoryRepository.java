package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.TestHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface TestHistoryRepository extends MongoRepository<TestHistory, UUID> {
    Optional<TestHistory> findByUserIdAndTestId(UUID userId, UUID testId);
}
