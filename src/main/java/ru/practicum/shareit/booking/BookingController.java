package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
@Validated
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping
    BookingDto addBooking(@RequestHeader("X-Sharer-User-Id") Integer userId, @Valid @RequestBody BookingDto bookingDto) {
        return bookingService.addBooking(bookingDto, userId);
    }

    @GetMapping("/{bookingId}")
    BookingDto getBooking(@RequestHeader("X-Sharer-User-Id") Integer userId,
                          @PathVariable("bookingId") Integer bookingId) {
        return bookingService.getBookingById(userId, bookingId);
    }

    @PatchMapping("/{bookingId}")
    BookingDto confirmBooking(@RequestHeader("X-Sharer-User-Id") Integer userId,
                              @PathVariable("bookingId") Integer bookingId,
                              @RequestParam(name = "approved") Boolean approved) {
        return bookingService.confirmBooking(bookingId, userId, approved);
    }

    @GetMapping("/owner")
    Collection<BookingDto> getBookingByOwner(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                             @RequestParam(name = "state", defaultValue = "ALL") String state,
                                             @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                             @RequestParam(defaultValue = "10") @Positive Integer size) {
        return bookingService.getBookingByOwner(userId, state, from, size);
    }

    @GetMapping
    Collection<BookingDto> getBookingByBooker(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                              @RequestParam(name = "state", defaultValue = "ALL") String state,
                                              @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                              @RequestParam(defaultValue = "10") @Positive Integer size) {
        return bookingService.getBookingByBooker(userId, state, from, size);
    }
}
