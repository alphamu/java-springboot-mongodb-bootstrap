package com.alimuzaffar.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {
    @Query("{document_id: ?0}")
    User findByUserId(String id);
}
