package ru.practicum.shareit.booking.exception;

public class BookingUnsupportedStatusException extends RuntimeException {
    public BookingUnsupportedStatusException(String message) {
        super(message);
    }
}
