package com.plasmadrive.myspringrest.rest1;

public class CustomerServiceWrapsRuntimeException extends CustomerServiceException{
    public CustomerServiceWrapsRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerServiceWrapsRuntimeException(Throwable cause) {
        super(cause);
    }

}
