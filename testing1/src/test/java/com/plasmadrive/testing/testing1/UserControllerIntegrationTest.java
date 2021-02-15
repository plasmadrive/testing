package com.plasmadrive.testing.testing1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private UserService userService;



    @Test
    public void testFindAllByFirstAndLastName() throws Exception {
        given(this.userService.findAllUsersByFirstAndLastName("Fred","Foobar"))
                .willReturn(Collections.singletonList(new User("Fred","Foobar","manager")));

        List<User> users = testRestTemplate.getForObject("http://localhost:" + serverPort + "/user?firstName=Fred&lastName=Foobar",
                List.class);
        assertThat(users).hasSize(1);
       // assertThat(users).contains()

    }

/*
    @Configuration
    static class Config {
        @MockBean
        private UserService userService;

        @Bean
        public UserService userService()throws Exception {
            given(this.userService.findAllUsersByFirstAndLastName("Fred","Foobar"))
            .willReturn(Collections.singletonList(new User("Fred","Foobar","manager")));
            return this.userService;
        }
    }
*/

}
