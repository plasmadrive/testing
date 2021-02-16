package com.plasmadrive.myspringrest.rest1;

import static org.assertj.core.api.Assertions.as;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @MockBean
    private CustomerRepository customerRepository;


    private CustomerService customerService;

    @BeforeEach
    public void before() {
        customerService = new CustomerService(customerRepository);
    }

    @Test
    public void testFindByIdSucceeds() {
        Customer fred = new Customer("Fred","Foobar","aaa", 42);
        fred.setId(1L);
        given(this.customerRepository.findById(1L))
        .willReturn(Optional.of(fred));
        assertThat(customerService.findById(1L)).isEqualTo(fred);
    }

    @Test
    public void testFindByIdFails() {
        Customer fred = new Customer("Fred","Foobar","aaa", 42);
        fred.setId(1L);
        given(this.customerRepository.findById(1L))
                .willReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> customerService.findById(1L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer 1L not found");
    }

    @Test
    public void testCreate() throws Exception {
        Customer fred = new Customer("Fred","Foobar","aaa",42);

        given(customerRepository.findCustomerBySsn("aaa"))
                .willReturn(Optional.ofNullable(null));

        given(customerRepository.save(fred))
                .willReturn(fred);
        assertThat(customerService.create(fred)).isEqualTo(fred);
    }

    @Test
    public void testCreateAllReadyExists() throws Exception {
        Customer fred = new Customer("Fred","Foobar","aaa",42);
        given(customerRepository.findCustomerBySsn("aaa"))
                .willReturn(Optional.of(fred));

        assertThatThrownBy(() -> customerService.create(fred))
                .isInstanceOf(CustomerAlreadyExistsException.class)
                .hasMessage("Customer ssn aaa already exists");
    }

    @Test
    public void testFindBySsn() throws Exception {
        Customer fred = new Customer("Fred","Foobar","aaa",42);
        fred.setId(1L);
        given(customerRepository.findCustomerBySsn("aaa")).willReturn(Optional.of(fred));
        assertThat(customerService.findAllBy(
                Optional.ofNullable(null),
                Optional.ofNullable(null),
                Optional.of("aaa")))
                .isEqualTo(Collections.singletonList(fred));
    }

    @Test
    public void testFindBySSnCustomerNotFound() throws Exception {
        given(customerRepository.findCustomerBySsn("aaa")).willReturn(Optional.ofNullable(null));

        assertThat(customerService.findAllBy(
                Optional.ofNullable(null),
                Optional.ofNullable(null),
                Optional.of("aaa")))
                .isEqualTo(Collections.emptyList());
    }

    @Test
    public void testFindAllByFirstNameOnly() {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        Customer fred2 =  new Customer("Fred","Foobar1","bbb",21);
        fred1.setId(1L);
        fred2.setId(2L);

        given(customerRepository.findCustomerByFirstName("Fred")).willReturn(Arrays.asList(fred1,fred2));
        assertThat(customerService.findAllBy(
                Optional.of("Fred"),
                Optional.ofNullable(null),
                Optional.ofNullable(null))).hasSameElementsAs(Arrays.asList(fred1,fred2));

    }

    @Test
    public void testFindAllByLastNameOnly() {
        Customer foobar1 = new Customer("Fred","Foobar","aaa",42);
        Customer foobar2 =  new Customer("Fiona","Foobar","bbb",21);
        foobar1.setId(1L);
        foobar2.setId(2L);

        given(customerRepository.findCustomerByFirstName("Fred")).willReturn(Arrays.asList(foobar1,foobar2));
        assertThat(customerService.findAllBy(
                Optional.of("Fred"),
                Optional.ofNullable(null),
                Optional.ofNullable(null))).hasSameElementsAs(Arrays.asList(foobar1,foobar2));

    }

    @Test
    public void testFindAllByFirstAndLastName() {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        Customer fred2 =  new Customer("Fred","Foobar","bbb",21);
        Customer fred3 =  new Customer("Fred","Foobar","ccc",23);
        fred1.setId(1L);
        fred2.setId(2L);
        fred2.setId(3L);

        given(customerRepository.findCustomerByFirstNameAndLastName("Fred","Foobar")).willReturn(Arrays.asList(fred1,fred2,fred3));
        assertThat(customerService.findAllBy(
                Optional.of("Fred"),
                Optional.of("Foobar"),
                Optional.ofNullable(null))).hasSameElementsAs(Arrays.asList(fred1,fred2,fred3));

    }

    @Test
    public void testFindWithSsnAndFirstNameAndLastName() {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        Customer fred2 =  new Customer("Fred","Foobar","bbb",21);
        Customer fred3 =  new Customer("Fred","Foobar","ccc",23);

        given(customerRepository.findCustomerBySsn("aaa")).willReturn(Optional.of(fred1));
        given(customerRepository.findCustomerByFirstNameAndLastName("firstName","lastName"))
                .willReturn(Arrays.asList(fred1,fred2,fred3));

        assertThat(customerService.findAllBy(
                Optional.of("Fred"),
                Optional.of("Foobar"),
                Optional.of("aaa")))
                .hasSameElementsAs(Collections.singletonList(fred1));
    }

    @Test
    public void testFindWithFirstNameButDoesntExist() {
        given(customerRepository.findCustomerByFirstName("fred")).willReturn(Collections.emptyList());

        assertThat(customerService.findAllBy(
                Optional.of("Fred"),
                Optional.ofNullable(null),
                Optional.ofNullable(null)))
                .hasSameElementsAs(Collections.emptyList());
    }

    @Test
    public void testDeleteCustomerNotExists() {

        given(customerRepository.findById(1L)).willReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> customerService.delete(1L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer " + 1L + " not found");
    }

    @Test
    public void testSaveCustomerDoesNotExist() {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        given(customerRepository.findById(1L)).willReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> customerService.save(1L,fred1))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer " + 1L + " not found");
    }

}
