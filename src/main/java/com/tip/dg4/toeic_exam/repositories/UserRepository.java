package com.tip.dg4.toeic_exam.repositories;

import com.tip.dg4.toeic_exam.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
