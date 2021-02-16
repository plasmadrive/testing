package com.plasmadrive.myspringrest.rest1;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable long id) throws Exception {
        return ResponseEntity.ok().body(customerService.findById(id));

    }

    /*
    @GetMapping
    public ResponseEntity<Customer> get(@RequestParam(name="ssn") String ssn){
        return ResponseEntity.ok().body(customerService.findBySsn(ssn));

    }
*/
    @GetMapping
    public ResponseEntity<List<Customer>> list(@RequestParam(name="firstName",required = false) String firstName,
                                               @RequestParam(name="lastName", required = false) String lastName,
                                               @RequestParam(name="ssn",required = false)String ssn){
       return ResponseEntity.ok().body(customerService.findAllBy(Optional.ofNullable(firstName),
               Optional.ofNullable(lastName),
               Optional.ofNullable(ssn)));
    }




    @PostMapping
    public ResponseEntity<Customer> post(@RequestBody Customer customer) throws Exception {
        Customer createdCustomer = customerService.create(customer);
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}")
                .buildAndExpand(createdCustomer.getId()).toUri();
        return ResponseEntity.created(uri).body(createdCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) throws Exception {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> put(@PathVariable long id, @RequestBody Customer customer) throws Exception {
        customerService.save(id,customer);
        return ResponseEntity.noContent().build();
    }


}
