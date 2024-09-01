package com.srimani.quickcart.exception;

public class UserNotExistsException extends Exception {
    public UserNotExistsException(String username) {
        super("There is no user exists with "+username);
    }

    public UserNotExistsException(long userId) {
        super("There is no user exists with "+userId);
    }
}
