package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.PartTest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface PartTestRepository extends MongoRepository<PartTest, UUID> {
    boolean existsByPracticePartIdAndName(UUID practicePartId, String name);
    List<PartTest> findByPracticePartId(UUID practicePartId);
}