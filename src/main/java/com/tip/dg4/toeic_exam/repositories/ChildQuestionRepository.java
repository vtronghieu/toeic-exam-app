package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.ChildQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ChildQuestionRepository extends MongoRepository<ChildQuestion, UUID> {
    List<ChildQuestion> findByQuestionId(UUID questionId);
    void deleteByQuestionId(UUID questionId);
}
