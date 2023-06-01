package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends MongoRepository<Account, UUID> {
    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);
}
