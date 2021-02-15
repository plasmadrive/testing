package com.plasmadrive.testing.testing1;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsersByFirstAndLastName(String firstName,String lastName)
            throws UserServiceException {
        try {
            return userRepository.findAllByFirstNameAndAndLastName(firstName,lastName);
        } catch (Throwable throwable) {
            throw new UserServiceException("This is a friendly message", throwable);
        }
    }
}
