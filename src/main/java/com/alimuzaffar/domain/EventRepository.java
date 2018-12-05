package com.alimuzaffar.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EventRepository extends MongoRepository<Event, String>, EventRepositoryCustom {
    @Query("{document_id: ?0}")
    Event findByEventId(String id);
}
