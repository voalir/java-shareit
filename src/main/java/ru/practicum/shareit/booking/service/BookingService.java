package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.Collection;

public interface BookingService {
    BookingDto addBooking(BookingDto bookingDto, Integer userId);

    BookingDto confirmBooking(Integer bookingId, Integer userId, Boolean approved);

    Collection<BookingDto> getBookingByOwner(Integer userId, String state, Integer from, Integer size);

    Collection<BookingDto> getBookingByBooker(Integer userId, String state, Integer from, Integer size);

    BookingDto getBookingById(Integer userId, Integer bookingId);
}
