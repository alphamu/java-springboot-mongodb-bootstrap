package com.alimuzaffar;

import com.alimuzaffar.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Optional;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, EventRepository eventRepository) {

        return args -> {
            userRepository.updateIds();
            eventRepository.updateIds();
            // These are just tests to see if our repo is working
            User obj = userRepository.findByUserId("507f191e810c19729de860e1");
            Event obj2 = eventRepository.findByEventId("507f191e810c19729de8aae0");
            System.out.println(obj);
            System.out.println(obj2);
            System.out.println("          ____  \n" +
                    "        o8%8888,    \n" +
                    "      o88%8888888.  \n" +
                    "     8'-    -:8888b   \n" +
                    "    8'         8888  \n" +
                    "   d8.-=. ,==-.:888b  \n" +
                    "   >8 `~` :`~' d8888   \n" +
                    "   88         ,88888   \n" +
                    "   88b. `-~  ':88888  \n" +
                    "   888b ~==~ .:88888 \n" +
                    "   88888o--:':::8888      \n" +
                    "   `88888| :::' 8888b  \n" +
                    "   8888^^'       8888b  \n" +
                    "  d888           ,%888b.   \n" +
                    " d88%            %%%8--'-.  \n" +
                    "/88:.__ ,       _%-' ---  -  \n" +
                    "    '''::===..-'   =  --.");
            System.out.println("All good!");
        };

    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,
                                       MongoMappingContext context) {

        MappingMongoConverter converter =
                new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

        return mongoTemplate;

    }

}
