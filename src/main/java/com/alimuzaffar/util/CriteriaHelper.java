package com.alimuzaffar.util;

import com.alimuzaffar.model.Filter;
import org.springframework.data.mongodb.core.query.Criteria;

public class CriteriaHelper {
    public static Criteria createCriteriaFor(Filter filter, Criteria criteria) {
        if (filter.rangeExists()) {
            if (criteria == null) {
                criteria = new Criteria();
            }
            criteria = criteria.andOperator(
                    Criteria.where(
                            filter.getAttribute())
                            .gte(filter.getRange().getFrom()),
                    Criteria.where(
                            filter.getAttribute())
                            .lte(filter.getRange().getFrom())
            );
        } else {
            if (criteria == null) {
                criteria = Criteria.where(filter.getAttribute());
            }
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
