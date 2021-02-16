package com.plasmadrive.myspringrest.rest1;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findById(long id)  {

            return customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer " + id + "L not found"));

    }

    public Customer create(Customer customer) {

            Optional<Customer> optionalCustomer = customerRepository.findCustomerBySsn(customer.getSsn());
            optionalCustomer.ifPresent(c -> {
                throw new CustomerAlreadyExistsException("Customer ssn " + c.getSsn() + " already exists");
            });
            return customerRepository.save(customer);

    }



    public List<Customer> findAllBy(Optional<String> firstName, Optional<String> lastName, Optional<String> ssn) {
        return ssn.map(s -> customerRepository.findCustomerBySsn(s).map(Collections::singletonList).orElse(Collections.emptyList()))
                .orElse(findAllByFirstNameAndOrLastName(firstName,lastName));

    }

    private List<Customer> findAllByFirstNameAndOrLastName(Optional<String> firstName, Optional<String> lastName) {
        try {
            return firstName.map(fn -> (lastName).map(ln -> customerRepository.findCustomerByFirstNameAndLastName(fn, ln))
                    .orElse(customerRepository.findCustomerByFirstName(firstName.get()))).
                    orElse(lastName.map(ln -> customerRepository.findCustomerByLastName(ln))
                            .orElse(convertIterableToList(customerRepository.findAll())));
        } catch (Throwable t) {
            throw new CustomerServiceWrapsRuntimeException("Exception findingByFirstNameAndOrLastName",t);
        }
    }

    private List<Customer> convertIterableToList(Iterable<Customer> it) {
        return StreamSupport.stream(it.spliterator(),false).collect(Collectors.toList());
    }



    public void delete(long id) {
       Optional<Customer> customer = customerRepository.findById(id);
       customer.orElseThrow(() -> new CustomerNotFoundException("Customer " + id + " not found"));
       customer.ifPresent(c -> customerRepository.delete(c));


    }

    public void save(long id,Customer customer) {
        Optional<Customer> customer1 = customerRepository.findById(id);
        customer1.orElseThrow(() -> new CustomerNotFoundException("Customer " + id + " not found"));
        customer1.ifPresent(c -> {customer1.get().setId(1L);customerRepository.save(customer1.get());});
    }
}
