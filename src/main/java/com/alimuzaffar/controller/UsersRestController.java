package com.alimuzaffar.controller;

import com.alimuzaffar.domain.User;
import com.alimuzaffar.model.ResponseWrapper;
import com.alimuzaffar.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersRestController {

    final UsersService usersService;

    public UsersRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<List<User>>> getSearchUser(@RequestParam(value="filter") List<String> filters) {
        ResponseWrapper<List<User>> resp = new ResponseWrapper<>(false, null, null);
        try {
            List<User> tests = usersService.getUsers(filters);
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
    public ResponseEntity<ResponseWrapper<User>> getUser(@PathVariable final String id) {
        ResponseWrapper<User> resp = new ResponseWrapper<>(false, null, null);
        User user = usersService.getUser(id);
        if (user != null) {
            resp.setSuccess(true);
            resp.setData(user);
        } else {
            resp.setError("User not found");
        }
        return new ResponseEntity<>(resp, resp.isSuccess()? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
