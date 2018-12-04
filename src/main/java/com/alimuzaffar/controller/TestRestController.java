package com.alimuzaffar.controller;

import com.alimuzaffar.model.Test;
import com.alimuzaffar.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/json/test")
@CrossOrigin
public class TestRestController {

    final TestService testService;

    public TestRestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Test>> getTest() {
        List<Test> tests = testService.getTests();
        if (tests != null) {
            return new ResponseEntity<>(tests, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(tests, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Test> getTest(@PathVariable final String id) {
        Test test = testService.getTest(id);
        if (test != null) {
            return new ResponseEntity<>(test, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(test, HttpStatus.NOT_FOUND);
        }
    }

    //@PostMapping("/")
    //public ResponseEntity<Test> createTest(@RequestBody Test test) {
//Test savedTest = testService.createTeste(test);
    //      if (savedTest != null) {
    //        return new ResponseEntity(savedTest, HttpStatus.CREATED);
    //  } else {
    //    return new ResponseEntity(savedTest, HttpStatus.INTERNAL_SERVER_ERROR);
    //}
    //}

    @PutMapping("/{id}")
    public ResponseEntity<Test> updateTest(@PathVariable final String id, @RequestBody Test test) {
        Test updatedTest = testService.updateTest(id, test);
        if (updatedTest != null) {
            return new ResponseEntity<Test>(updatedTest, HttpStatus.OK);
        } else {
            return new ResponseEntity<Test>(updatedTest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
