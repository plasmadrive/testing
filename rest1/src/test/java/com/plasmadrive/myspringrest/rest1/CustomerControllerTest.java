package com.plasmadrive.myspringrest.rest1;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

@WebMvcTest(CustomerController.class)
@AutoConfigureJsonTesters
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private JacksonTester<Customer> json;

    @Test
    public void testGet() throws Exception {
        Customer fred = new Customer("Fred","Foobar","aaa",45);
        given(this.customerService.findById(1L))
                .willReturn(fred);
        mvc.perform(MockMvcRequestBuilders.get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",is("Fred")))
                .andExpect(jsonPath("$.lastName",is("Foobar")))
                .andExpect(jsonPath("$.ssn",is("aaa")))
                .andExpect(jsonPath("$.age",is(45)));
    }

    @Test
    public void testGetIdNotLong() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/customers/x"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPost() throws Exception {
       // Customer fred = new Customer("Fred","Foobar","aaa",45);
        String jsonFred = "{\"firstName\" : \"Fred\", \"lastName\" : \"Foobar\", \"ssn\": \"aaa\",\"age\" :\"45\"}";
        Customer fred = json.parse(jsonFred).getObject();
        fred.setId(1L);
        given(this.customerService.create(fred))
                .willReturn(fred);

        mvc.perform(MockMvcRequestBuilders.post("/customers").content(jsonFred)
                .accept("application/json")
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",isA(Integer.class)))
                .andExpect(jsonPath("$.firstName", is("Fred")))
                .andExpect(header().string("Location","http://localhost/customers/1"));

    }

    @Test
    public void testListForSsn() throws Exception {
        String jsonFred = "{\"firstName\" : \"Fred\", \"lastName\" : \"Foobar\", \"ssn\": \"aaa\",\"age\" :\"45\"}";
        Customer fred = json.parse(jsonFred).getObject();
        fred.setId(1L);

        given(this.customerService.findAllBy(Optional.ofNullable(null),
                Optional.ofNullable(null),
                Optional.of("aaa")))
                .willReturn(Collections.singletonList(fred));
        mvc.perform(MockMvcRequestBuilders.get("/customers?ssn=aaa")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName",is("Fred")))
                .andExpect(status().isOk());
    }





}
