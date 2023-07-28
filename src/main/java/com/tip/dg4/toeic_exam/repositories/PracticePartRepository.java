package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.PracticePart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface PracticePartRepository extends MongoRepository<PracticePart, UUID> {
    boolean existsByPracticeIdAndName(UUID practiceId, String name);
    List<PracticePart> findByPracticeId(UUID practiceId);
}