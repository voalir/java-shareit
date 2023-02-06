package ru.practicum.shareit.booking.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

class BookingMapperTest {

    @Test
    void toBookingDto() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Booking booking = getBooking(localDateTime);
        BookingDto bookingDto = BookingMapper.toBookingDto(booking);
        Assertions.assertThat(bookingDto)
                .hasFieldOrPropertyWithValue("id", booking.getId())
                .hasFieldOrProperty("item")
                .hasFieldOrProperty("booker")
                .hasFieldOrPropertyWithValue("status", booking.getStatus())
                .hasFieldOrPropertyWithValue("start", booking.getStart())
                .hasFieldOrPropertyWithValue("end", booking.getEnd());
    }

    @Test
    void toShortBookingDto() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Booking booking = getBooking(localDateTime);
        ShortBookingDto shortBookingDto = BookingMapper.toShortBookingDto(booking);
        Assertions.assertThat(shortBookingDto)
                .hasFieldOrPropertyWithValue("id", booking.getId())
                .hasFieldOrPropertyWithValue("bookerId", booking.getBooker().getId());
    }

    @Test
    void toBooking() {
        LocalDateTime localDateTime = LocalDateTime.now();
        BookingDto bookingDto = getBookingDto(localDateTime);
        Booking booking = BookingMapper.toBooking(bookingDto);
        Assertions.assertThat(booking)
                .hasFieldOrPropertyWithValue("id", bookingDto.getId())
                .hasFieldOrProperty("item")
                .hasFieldOrProperty("booker")
                .hasFieldOrPropertyWithValue("status", bookingDto.getStatus())
                .hasFieldOrPropertyWithValue("start", bookingDto.getStart())
                .hasFieldOrPropertyWithValue("end", bookingDto.getEnd());
    }

    BookingDto getBookingDto(LocalDateTime localDateTime) {
        return new BookingDto(1,
                localDateTime,
                localDateTime,
                1,
                getItemDto(),
                new UserDto(1, "name", "mail"),
                BookingStatus.WAITING);
    }

    Booking getBooking(LocalDateTime localDateTime) {
        return new Booking(1,
                localDateTime,
                localDateTime,
                new Item(1, "item", "item", true, null, null),
                new User(1, "name", "mail"),
                BookingStatus.WAITING);
    }

    ItemDto getItemDto() {
        return new ItemDto(
                1,
                "name item",
                "description",
                false,
                null,
                null,
                null,
                null
        );
    }
}