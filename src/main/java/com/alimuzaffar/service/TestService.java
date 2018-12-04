package com.alimuzaffar.service;

import com.alimuzaffar.domain.DomainRepository;
import com.alimuzaffar.model.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;

import java.util.List;


@Service
public class TestService {
    @Autowired
    DomainRepository domainRepository;

    private final Logger log = LoggerFactory.getLogger(TestService.class);

    public List<Test> getTests() {
        return Collections.emptyList();
    }

    public Test getTest(final String id) {
        return new Test();
    }


    public Test updateTest(final String id, Test test) {
        return test;
    }

}
