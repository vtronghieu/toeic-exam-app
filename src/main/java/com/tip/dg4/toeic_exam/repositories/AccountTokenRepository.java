package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.AccountToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountTokenRepository extends MongoRepository<AccountToken, UUID> {
    Optional<AccountToken> findByAccountId(UUID accountId);
}
