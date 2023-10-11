package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.enums.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends MongoRepository<Question, UUID> {
    long countByType(QuestionType questionType);

    Page<Question> findByType(QuestionType questionType, Pageable pageable);

    long countByObjectTypeId(UUID objectTypeId);

    Page<Question> findByObjectTypeId(UUID objectTypeId, Pageable pageable);

    List<Question> findByObjectTypeId(UUID objectTypeId);
}
