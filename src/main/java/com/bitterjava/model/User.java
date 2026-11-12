package com.bitterjava.model;

public record User(long id, String username, String passwordHash) {
}
