package com.plasmadrive.testing.testing1;

public class UserServiceException extends Exception {
    private Throwable wrappedThrowable;
    private String friendlyErrorMessage;

    public UserServiceException(String friendlyErrorMessage,Throwable wrappedThrowable) {
        super();
        this.friendlyErrorMessage = friendlyErrorMessage;
        this.wrappedThrowable = wrappedThrowable;
    }
}
