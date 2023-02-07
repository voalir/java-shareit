package ru.practicum.shareit.booking.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.exception.BookingAccessException;
import ru.practicum.shareit.booking.exception.BookingCheckException;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.exception.ItemAvailableException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingServiceImplTest {

    @Autowired
    BookingService bookingService;

    @Test
    @Order(0)
    @Sql(value = {"/DropTables.sql", "/BookingTestPrepare.sql"})
    void addBooking() {
        BookingDto bookingDto = getBookingDto(1, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        Optional<BookingDto> createdBookingDto = Optional.of(bookingService.addBooking(bookingDto, 3));
        assertThat(createdBookingDto)
                .isPresent()
                .hasValueSatisfying(dto -> {
                    assertThat(dto).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(dto.getItem()).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(dto.getBooker()).hasFieldOrPropertyWithValue("id", 3);
                    assertThat(dto).hasFieldOrPropertyWithValue("status", BookingStatus.WAITING);
                });
        assertThrows(ItemNotFoundException.class, () -> bookingService.addBooking(bookingDto, 1));
        Optional<BookingDto> createdBookingDto2 = Optional.of(bookingService.addBooking(bookingDto, 2));
        assertThat(createdBookingDto2)
                .isPresent()
                .hasValueSatisfying(dto -> {
                    assertThat(dto).hasFieldOrPropertyWithValue("id", 2);
                    assertThat(dto.getItem()).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(dto.getBooker()).hasFieldOrPropertyWithValue("id", 2);
                    assertThat(dto).hasFieldOrPropertyWithValue("status", BookingStatus.WAITING);
                });
        BookingDto bookingDto2 = getBookingDto(2, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        assertThrows(ItemAvailableException.class, () -> bookingService.addBooking(bookingDto2, 1));
    }

    @Test
    @Order(1)
    void confirmBooking() {
        Optional<BookingDto> bookingDto = Optional.of(bookingService.confirmBooking(1, 1, true));

        assertThat(bookingDto)
                .isPresent()
                .hasValueSatisfying(dto -> {
                    assertThat(dto).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(dto.getItem()).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(dto.getBooker()).hasFieldOrPropertyWithValue("id", 3);
                    assertThat(dto).hasFieldOrPropertyWithValue("status", BookingStatus.APPROVED);
                });
        assertThrows(BookingCheckException.class, () -> bookingService.confirmBooking(1, 1, true));
        assertThrows(BookingAccessException.class, () -> bookingService.confirmBooking(2, 2, true));
        assertThrows(BookingAccessException.class, () -> bookingService.confirmBooking(2, 1, true));

        Optional<BookingDto> bookingDtoRejected = Optional.of(bookingService.confirmBooking(2, 1, false));
        assertThat(bookingDtoRejected)
                .isPresent()
                .hasValueSatisfying(dto -> {
                    assertThat(dto).hasFieldOrPropertyWithValue("id", 2);
                    assertThat(dto.getItem()).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(dto.getBooker()).hasFieldOrPropertyWithValue("id", 2);
                    assertThat(dto).hasFieldOrPropertyWithValue("status", BookingStatus.REJECTED);
                });
    }

    @Test
    @Order(2)
    void getBookingByOwnerAll() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByOwner(1, "ALL", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(2));
    }

    @Test
    @Order(3)
    void getBookingByOwnerWaiting() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByOwner(1, "WAITING", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(0));
        BookingDto bookingDto = getBookingDto(1, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        Optional<BookingDto> createdBookingDtoFuture = Optional.of(bookingService.addBooking(bookingDto, 2));
        assertThat(createdBookingDtoFuture)
                .isPresent()
                .hasValueSatisfying(dto -> {
                    assertThat(dto).hasFieldOrPropertyWithValue("id", 3);
                    assertThat(dto.getItem()).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(dto.getBooker()).hasFieldOrPropertyWithValue("id", 2);
                    assertThat(dto).hasFieldOrPropertyWithValue("status", BookingStatus.WAITING);
                });
        bookingDtos = Optional.of(bookingService.getBookingByOwner(1, "WAITING", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(1));
    }

    @Test
    @Order(4)
    void getBookingByOwnerCURRENT() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByOwner(1, "CURRENT", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(2));
    }

    @Test
    @Order(5)
    void getBookingByOwnerPAST() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByOwner(1, "PAST", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(0));
        BookingDto bookingDto = getBookingDto(1, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
        Optional<BookingDto> createdBookingDtoFuture = Optional.of(bookingService.addBooking(bookingDto, 2));
        assertThat(createdBookingDtoFuture)
                .isPresent()
                .hasValueSatisfying(dto -> {
                    assertThat(dto).hasFieldOrPropertyWithValue("id", 4);
                    assertThat(dto.getItem()).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(dto.getBooker()).hasFieldOrPropertyWithValue("id", 2);
                    assertThat(dto).hasFieldOrPropertyWithValue("status", BookingStatus.WAITING);
                });
        bookingDtos = Optional.of(bookingService.getBookingByOwner(1, "PAST", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(1));
    }

    @Test
    @Order(6)
    void getBookingByOwnerFUTURE() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByOwner(1, "FUTURE", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(1));
    }

    @Test
    @Order(7)
    void getBookingByOwnerREJECTED() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByOwner(1, "REJECTED", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(1));
    }

    @Test
    @Order(9)
    void getBookingByBookerAll() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByBooker(2, "ALL", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(3));
        Optional<Collection<BookingDto>> bookingDtosSize2 = Optional.of(bookingService.getBookingByBooker(2, "ALL", 0, 2));
        assertThat(bookingDtosSize2)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(2));
    }

    @Test
    @Order(10)
    void getBookingByBookerWaiting() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByBooker(2, "WAITING", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(2));
    }

    @Test
    @Order(11)
    void getBookingByBookerCURRENT() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByBooker(2, "CURRENT", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(1));
    }

    @Test
    @Order(12)
    void getBookingByBookerPAST() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByBooker(2, "PAST", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(1));
    }

    @Test
    @Order(13)
    void getBookingByBookerFUTURE() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByBooker(2, "FUTURE", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(1));
    }

    @Test
    @Order(14)
    void getBookingByBookerREJECTED() {
        Optional<Collection<BookingDto>> bookingDtos = Optional.of(bookingService.getBookingByBooker(2, "REJECTED", 0, 10));
        assertThat(bookingDtos)
                .isPresent()
                .hasValueSatisfying(element -> Assertions.assertThat(element).hasSize(1));
    }

    @Test
    @Order(15)
    void getBookingById() {
        Optional<BookingDto> bookingDto = Optional.of(bookingService.getBookingById(1, 1));
        assertThat(bookingDto)
                .isPresent()
                .hasValueSatisfying(dto -> {
                    assertThat(dto).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(dto.getItem()).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(dto.getBooker()).hasFieldOrPropertyWithValue("id", 3);
                    assertThat(dto).hasFieldOrPropertyWithValue("status", BookingStatus.APPROVED);
                });
        assertThrows(BookingAccessException.class, () -> bookingService.getBookingById(2, 1));
        assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(2, 99));
    }

    BookingDto getBookingDto(Integer itemId, LocalDateTime start, LocalDateTime end) {
        return new BookingDto(null,
                start,
                end,
                itemId,
                null,
                new UserDto(1, "name", "mail"),
                BookingStatus.WAITING);
    }

}