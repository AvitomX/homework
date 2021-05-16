package ru.tfs.spring.data.exception;

public class UserPhoneUniqueException extends Exception {
    public UserPhoneUniqueException(String message) {
        super(message);
    }
}
