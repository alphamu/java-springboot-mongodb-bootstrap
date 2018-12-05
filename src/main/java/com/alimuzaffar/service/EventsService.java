package com.alimuzaffar.service;

import com.alimuzaffar.domain.Event;
import com.alimuzaffar.domain.EventRepository;
import com.alimuzaffar.domain.User;
import com.alimuzaffar.domain.UserRepository;
import com.alimuzaffar.model.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class EventsService {
    @Autowired
    EventRepository eventRepository;

    private final Logger log = LoggerFactory.getLogger(EventsService.class);

    public List<Event> getEvents(List<String> filtersStringArray) throws Exception {
        List<Filter> filters = Filter.fromJsonBase64String(filtersStringArray);
        return eventRepository.search(filters);
    }

    public Event getEvent(final String id) {
        return eventRepository.findByEventId(id);
    }



}
