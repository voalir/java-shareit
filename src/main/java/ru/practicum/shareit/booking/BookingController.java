package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping
    BookingDto addBooking(@RequestHeader("X-Sharer-User-Id") Integer userId, @RequestBody BookingDto bookingDto) {
        return bookingService.addBooking(bookingDto, userId);
    }

    @GetMapping("/{bookingId}")
    BookingDto getBooking(@RequestHeader("X-Sharer-User-Id") Integer userId,
                          @PathVariable("bookingId") Integer bookingId) {
        return bookingService.getBookingById(userId, bookingId);
    }

    @PatchMapping("/{bookingId}")
    BookingDto updateBooking(@RequestHeader("X-Sharer-User-Id") Integer userId,
                             @PathVariable("bookingId") Integer bookingId,
                             @RequestParam(name = "approved") Boolean approved) {
        return bookingService.confirmBooking(bookingId, userId, approved);
    }

    @GetMapping("/owner")
    Collection<BookingDto> getBookingByOwner(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                             @RequestParam(name = "state", defaultValue = "ALL") BookingState state) {
        return bookingService.getBookingByOwner(userId, state);
    }

    @GetMapping
    Collection<BookingDto> getBookingByBooker(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                              @RequestParam(name = "state", defaultValue = "ALL") BookingState state) {
        return bookingService.getBookingByBooker(userId, state);
    }
}
