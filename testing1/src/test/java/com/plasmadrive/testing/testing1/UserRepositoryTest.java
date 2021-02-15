package com.plasmadrive.testing.testing1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    private User user1,user2,user3;


    @BeforeEach
    public void setUp() throws Exception {
        user1 = new User("Fred","Foobar","manager");
        testEntityManager.persist(user1);

        user2 = new User("Fiona","Foobar","ceo");
        testEntityManager.persist(user2);

        user3 = new User("Fred","Flintstone","driver");
        testEntityManager.persist(user3);
    }


    @Test
    public void testFindAllByFirstName() throws Exception {
        List<User> users = userRepository.findAllByFirstName("Fred");
        assertThat(users).hasSize(2);
        assertThat(users).contains(user1,user3);

    }

    @Test
    public void testFindAllByLastName() throws Exception {
        List<User> users = userRepository.findAllByLastName("Foobar");
        assertThat(users).hasSize(2);
        assertThat(users).contains(user1,user2);
    }

    @Test
    public void testFindAllByFirstAndLastName() throws Exception {
        List<User> users = userRepository.findAllByFirstNameAndAndLastName("Fred","Foobar");
        assertThat(users).hasSize(1);
        assertThat(users).contains(user1);
    }
}
