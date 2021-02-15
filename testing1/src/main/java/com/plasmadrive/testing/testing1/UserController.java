package com.plasmadrive.testing.testing1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> findAllUsersByFirstAndLastName(@RequestParam(name="firstName") String firstName,
                                                         @RequestParam(name="lastName") String lastName) {


        try {
            List<User> users = userService.findAllUsersByFirstAndLastName(firstName, lastName);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity<>(null,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
