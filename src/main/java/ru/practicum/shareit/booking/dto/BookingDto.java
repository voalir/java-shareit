package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Date;

/**
 * TODO Sprint add-bookings.
 */
@Data
@AllArgsConstructor
public class BookingDto {

    int id;
    Date start;
    Date end;
    Item item;
    User booker;
    BookingStatus status;

    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem(),
                booking.getBooker(),
                booking.getStatus()
        );
    }
}
