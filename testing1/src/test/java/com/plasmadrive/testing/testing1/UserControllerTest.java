package com.plasmadrive.testing.testing1;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void TestFindByFirstAndLastName() throws Exception {
        User user = new User("Fred","Foobar","manager");
        String userJson = "[{\"firstName\" : \"Fred\", \"lastName\" : \"Foobar\",\"position\" : \"manager\"}]";

        given(this.userService.findAllUsersByFirstAndLastName("Fred","Foobar"))
        .willReturn(Collections.singletonList(user));

        mvc.perform(MockMvcRequestBuilders.get("/user?firstName=Fred&lastName=Foobar"))
                .andExpect(status().isOk())
                .andExpect(content().json(userJson));
    }

    @Test
    public void TestFindByFirstAndLastNameException () throws Exception {
        User user = new User("Fred","Foobar","manager");
        //String userJson = "[{\"firstName\" : \"Fred\", \"lastName\" : \"Foobar\",\"position\" : \"manager\"}]";

        given(this.userService.findAllUsersByFirstAndLastName("Fred","Foobar"))
                .willThrow(new UserServiceException("Something went wrong", new Throwable()));

        mvc.perform(MockMvcRequestBuilders.get("/user?firstName=Fred&lastName=Foobar"))
                .andExpect(status().is5xxServerError());
    }

}
