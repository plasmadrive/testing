package com.plasmadrive.myspringrest.rest1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testFindBySSn() throws Exception {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        Customer fred2 =  new Customer("Fred","Foobar","bbb",21);
        Customer fred3 =  new Customer("Fred","Foobar","ccc",23);


        testEntityManager.persist(fred1);
        testEntityManager.persist(fred2);
        testEntityManager.persist(fred3);

        customerRepository.findCustomerBySsn("aaa").map(c -> assertThat(c).isEqualTo(fred1));
    }

    @Test
    public void testFindBySSnNotFound() {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        Customer fred2 =  new Customer("Fred","Foobar","bbb",21);
        Customer fred3 =  new Customer("Fred","Foobar","ccc",23);


        testEntityManager.persist(fred1);
        testEntityManager.persist(fred2);
        testEntityManager.persist(fred3);

        assertThat(customerRepository.findCustomerBySsn("ddd").isEmpty()) ;
    }

    @Test
    public void testFindByFirstName() {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        Customer fred2 =  new Customer("Fiona","Foobar","bbb",21);
        Customer fred3 =  new Customer("Fred","Foobar","ccc",23);


        testEntityManager.persist(fred1);
        testEntityManager.persist(fred2);
        testEntityManager.persist(fred3);

        assertThat(customerRepository.findCustomerByFirstName("Fred")).hasSameElementsAs(Arrays.asList(fred1,fred3));

    }

    @Test
    public void testFindByFirstNameNotThere() {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        Customer fred2 =  new Customer("Fiona","Foobar","bbb",21);
        Customer fred3 =  new Customer("Fred","Foobar","ccc",23);


        testEntityManager.persist(fred1);
        testEntityManager.persist(fred2);
        testEntityManager.persist(fred3);

        assertThat(customerRepository.findCustomerByFirstName("Joe")).hasSameElementsAs(Collections.emptyList());

    }

    @Test
    public void testFindByLastName() {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        Customer fred2 =  new Customer("Fiona","Jones","bbb",21);
        Customer fred3 =  new Customer("Fred","Brown","ccc",23);


        testEntityManager.persist(fred1);
        testEntityManager.persist(fred2);
        testEntityManager.persist(fred3);

        assertThat(customerRepository.findCustomerByLastName("Foobar")).hasSameElementsAs(Arrays.asList(fred1));

    }

    @Test
    public void testFindByLastNameNotThere() {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        Customer fred2 =  new Customer("Fiona","Jones","bbb",21);
        Customer fred3 =  new Customer("Fred","Brown","ccc",23);


        testEntityManager.persist(fred1);
        testEntityManager.persist(fred2);
        testEntityManager.persist(fred3);

        assertThat(customerRepository.findCustomerByLastName("Green")).hasSameElementsAs(Collections.emptyList());

    }

    @Test
    public void testFindByFirstAndLastName() {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        Customer fred2 =  new Customer("Fiona","Jones","bbb",21);
        Customer fred3 =  new Customer("Fred","Brown","ccc",23);


        testEntityManager.persist(fred1);
        testEntityManager.persist(fred2);
        testEntityManager.persist(fred3);

        assertThat(customerRepository.findCustomerByFirstNameAndLastName("Fred","Foobar"))
                .hasSameElementsAs(Arrays.asList(fred1));

    }

    @Test
    public void testFindByFirstAndLastNameNotThere() {
        Customer fred1 = new Customer("Fred","Foobar","aaa",42);
        Customer fred2 =  new Customer("Fiona","Jones","bbb",21);
        Customer fred3 =  new Customer("Fred","Brown","ccc",23);


        testEntityManager.persist(fred1);
        testEntityManager.persist(fred2);
        testEntityManager.persist(fred3);

        assertThat(customerRepository.findCustomerByFirstNameAndLastName("Fred","Jones"))
                .hasSameElementsAs(Collections.emptyList());

    }


}
