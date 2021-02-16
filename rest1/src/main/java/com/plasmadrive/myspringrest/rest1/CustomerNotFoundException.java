package com.plasmadrive.myspringrest.rest1;

public class CustomerNotFoundException extends CustomerServiceException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
