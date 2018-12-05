package com.alimuzaffar.controller;

import com.alimuzaffar.domain.Event;
import com.alimuzaffar.domain.User;
import com.alimuzaffar.model.ResponseWrapper;
import com.alimuzaffar.service.EventsService;
import com.alimuzaffar.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventsRestController {

    final EventsService eventsService;

    public EventsRestController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<List<Event>>> getSearchEvent(@RequestParam(value="filter") List<String> filters) {
        ResponseWrapper<List<Event>> resp = new ResponseWrapper<>(false, null, null);
        try {
            List<Event> tests = eventsService.getEvents(filters);
            if (tests != null) {
                resp.setSuccess(true);
                resp.setData(tests);
            }
        } catch (Exception e) {
            resp.setError(e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.isSuccess()? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Event>> getEvent(@PathVariable final String id) {
        ResponseWrapper<Event> resp = new ResponseWrapper<>(false, null, null);
        Event event = eventsService.getEvent(id);
        if (event != null) {
            resp.setSuccess(true);
            resp.setData(event);
        } else {
            resp.setError("Event not found.");
        }
        return new ResponseEntity<>(resp, resp.isSuccess()? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
