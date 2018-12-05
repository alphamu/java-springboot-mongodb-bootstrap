package com.alimuzaffar.domain;

import com.alimuzaffar.model.Filter;
import com.alimuzaffar.util.CriteriaHelper;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collections;
import java.util.List;

//http://stackoverflow.com/questions/11880924/how-to-add-custom-method-to-spring-data-jpa
//http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.single-repository-behaviour
//Impl postfix of the name on it compared to the core repository interface
public class EventRepositoryImpl implements EventRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public long updateIds() {
        Query query = new Query(Criteria.where("document_id").exists(false));
        Update update = new Update();
        List<Event> events = mongoTemplate.find(query, Event.class, "events");
        long totalModified = 0;
        for (Event u : events) {
            update.set("document_id", u.getId());
            UpdateResult result = mongoTemplate.updateFirst(query, update, Event.class);
            totalModified += result.getModifiedCount();
        }

        return totalModified;
    }

    @Override
    public List<Event> search(List<Filter> filters) {
        Criteria c = null;
        for (Filter f : filters) {
            c = CriteriaHelper.createCriteriaFor(f, c);
        }
        Query query = new Query(c);
        List<Event> users = mongoTemplate.find(query, Event.class, "events");
        return users;
    }


}
