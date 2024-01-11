package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.Statistic;
import com.tip.dg4.toeic_exam.models.enums.StatisticStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StatisticRepository extends MongoRepository<Statistic, UUID> {
//    Optional<Statistic> findByUserIdAndTestIdAndStatus(UUID userId, UUID testId, StatisticStatus status);
//    List<Statistic> findListByUserIdAndTestId(UUID userId, UUID testId);
}
