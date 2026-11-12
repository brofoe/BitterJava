package com.bitterjava.service;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException() {
        super("That username is already taken.");
    }
}
