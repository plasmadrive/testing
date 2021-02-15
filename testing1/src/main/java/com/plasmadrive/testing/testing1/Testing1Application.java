package com.plasmadrive.testing.testing1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class Testing1Application {

    public static void main(String[] args) {
        SpringApplication.run(Testing1Application.class, args);
    }

    private UserRepository userRepository;

    /*
    @Bean
    CommandLineRunner run(UserRepository userRepository) throws Exception {
        return args -> {
            userRepository.save(new User("Fred","Foobar","manager"));
        };
    }
*/




}
