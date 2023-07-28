package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.PartLesson;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface PartLessonRepository extends MongoRepository<PartLesson, UUID> {
    boolean existsByPracticePartIdAndName(UUID practicePartId, String name);
    List<PartLesson> findByPracticePartId(UUID practicePartId);
}