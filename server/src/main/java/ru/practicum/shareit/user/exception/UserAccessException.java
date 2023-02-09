package ru.practicum.shareit.user.exception;

public class UserAccessException extends RuntimeException {
    public UserAccessException(String message) {
        super(message);
    }
}
