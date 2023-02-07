package ru.practicum.shareit.booking;

public class BookingUnsupportedStatusException extends RuntimeException {
    public BookingUnsupportedStatusException(String message) {
        super(message);
    }
}
