package com.alimuzaffar.domain;

import com.alimuzaffar.model.Filter;

import java.util.List;

public interface RepoCustom<T> {

    long updateIds();
    List<T> search(List<Filter> filters);
}
