package com.alimuzaffar.service;

import com.alimuzaffar.domain.User;
import com.alimuzaffar.domain.UserRepository;
import com.alimuzaffar.model.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersService {
//    @Autowired
    UserRepository userRepository;
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final Logger log = LoggerFactory.getLogger(UsersService.class);

    public List<User> getUsers(List<String> filtersStringArray) throws Exception {
        List<Filter> filters = Filter.fromJsonBase64String(filtersStringArray);
         return userRepository.search(filters);
    }

    public User getUser(final String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return userRepository.findByUserId(id);
    }



}
