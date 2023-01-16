package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.Collection;

public interface BookingService {
    BookingDto addBooking(BookingDto bookingDto, Integer userId);

    BookingDto confirmBooking(Integer bookingId, Integer userId, Boolean approved);

    Collection<BookingDto> getBookingByOwner(Integer userId, BookingState state);

    Collection<BookingDto> getBookingByBooker(Integer userId, BookingState state);

    BookingDto getBookingById(Integer userId, Integer bookingId);
}
