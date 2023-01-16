package ru.practicum.shareit.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.booking.BookingRepository;

public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;
}
