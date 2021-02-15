package com.plasmadrive.testing.testing1;

import static org.assertj.core.api.Assertions.as;
import static org.mockito.BDDMockito.*;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void before () {
        userService = new UserService(userRepository);
    }

    @Test
    public void testFindByFirstNameAndLastName() {
        User fred = new User("Fred","Foobar","manager");
        given(this.userRepository.findAllByFirstNameAndAndLastName("Fred","Foobar"))
                .willReturn(Arrays.asList(fred));


        try {
            List<User> users = userService.findAllUsersByFirstAndLastName("Fred","Foobar");
            assertThat(users).hasSize(1);
            assertThat(users).contains(fred);
        } catch (UserServiceException e) {

        }

    }
}
