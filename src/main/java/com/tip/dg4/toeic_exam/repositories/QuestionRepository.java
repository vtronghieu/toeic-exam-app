package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.QuestionType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends MongoRepository<Question, UUID> {
    boolean existsByTextQuestion(String textQuestion);
    boolean existsByAudioQuestion(String audioQuestion);
    boolean existsByTypeAndObjectTypeId(QuestionType questionType, UUID objectTypeId);
    List<Question> findByType(QuestionType questionType);
    List<Question> findByTypeAndObjectTypeIdIn(QuestionType questionType, List<UUID> objectTypeId);
}
