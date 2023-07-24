package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.Practice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PracticeRepository extends MongoRepository<Practice, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
