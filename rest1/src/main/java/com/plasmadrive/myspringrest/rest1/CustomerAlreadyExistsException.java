package com.plasmadrive.myspringrest.rest1;

public class CustomerAlreadyExistsException extends CustomerServiceException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
