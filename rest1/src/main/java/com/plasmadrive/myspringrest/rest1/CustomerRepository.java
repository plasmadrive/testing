package com.plasmadrive.myspringrest.rest1;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Optional<Customer> findCustomerBySsn(String ssn);
    List<Customer>  findCustomerByFirstName(String firstName);
    List<Customer>  findCustomerByLastName(String lastName);
    List<Customer> findCustomerByFirstNameAndLastName(String firstname,String lastName);
}
