package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.TestHistory;
import com.tip.dg4.toeic_exam.models.TestHistoryStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestHistoryRepository extends MongoRepository<TestHistory, UUID> {
    Optional<TestHistory> findByUserIdAndTestIdAndStatus(UUID userId, UUID testId, TestHistoryStatus status);
    List<TestHistory> findListByUserIdAndTestId(UUID userId, UUID testId);
}
