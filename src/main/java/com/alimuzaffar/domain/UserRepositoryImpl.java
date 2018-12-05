package com.alimuzaffar.domain;

import com.alimuzaffar.model.Filter;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
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
public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public long updateIds() {
        Query query = new Query(Criteria.where("document_id").exists(false));
        Update update = new Update();
        List<User> users = mongoTemplate.find(query, User.class, "users");
        long totalModified = 0;
        for (User u : users) {
            update.set("document_id", u.getId());
            UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
            totalModified += result.getModifiedCount();
        }

        return totalModified;
    }

    @Override
    public List<User> search(List<Filter> filters) {
        Criteria c = null;
        for (Filter f : filters) {
            c = createCriteriaFor(f, c);
        }
        Query query = new Query(c);
        List<User> users = mongoTemplate.find(query, User.class, "users");
        return users;
    }

    private Criteria createCriteriaFor(Filter filter, Criteria criteria) {
        if (criteria == null) {
            criteria = Criteria.where(filter.getAttribute());
        } else {
            criteria = criteria.and(filter.getAttribute());
        }
        if (filter.rangeExists()) {
            criteria = criteria.andOperator(
                    Criteria.where(
                            filter.getAttribute())
                            .gte(filter.getRange().getFrom()),
                    Criteria.where(
                            filter.getAttribute())
                            .lte(filter.getRange().getFrom())
            );
        } else {
            switch (filter.getOperator()) {
                case "eq":
                    criteria.is(filter.getValue());
                    break;
                case "gte":
                    criteria.gte(filter.getValue());
                    break;
                case "lte":
                    criteria.lte(filter.getValue());
                    break;
            }
        }
        return criteria;
    }

}
