package ru.practicum.shareit.booking.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@Sql(value = {"/src/test/resources/DropTables.sql", "/JpaRepositoryPrepare.sql"})
class BookingRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;

    @Test
    void findBookingByOwner() {
        Page<Booking> booking = bookingRepository.findBookingByOwner(1, PageRequest.of(0, 5));
        Assertions.assertThat(booking).hasSize(5);
    }

    @Test
    void findBookingByOwnerAndState() {
        List<Booking> booking = bookingRepository.findBookingByOwnerAndState(1, "WAITING", PageRequest.of(0, 5));
        Assertions.assertThat(booking).hasSize(3);
    }

    @Test
    void findBookingByBooker() {
        List<Booking> booking = bookingRepository.findBookingByBooker(2, PageRequest.of(0, 5));
        Assertions.assertThat(booking).hasSize(5);
    }

    @Test
    void findBookingByBookerAndItem() {
        List<Booking> booking = bookingRepository.findBookingByBookerAndItem(2, 1, PageRequest.of(0, 5));
        Assertions.assertThat(booking).hasSize(3);
    }

    @Test
    void findBookingByBookerAndState() {
        List<Booking> booking = bookingRepository.findBookingByBookerAndState(2, "WAITING", PageRequest.of(0, 5));
        Assertions.assertThat(booking).hasSize(3);
    }

    @Test
    void findBookingByBookerAndStateFuture() {
        List<Booking> booking = bookingRepository.findBookingByBookerAndStateFuture(2, PageRequest.of(0, 5));
        Assertions.assertThat(booking).hasSize(1);
    }

    @Test
    void findBookingByBookerAndStatePast() {
        List<Booking> booking = bookingRepository.findBookingByBookerAndStatePast(2, PageRequest.of(0, 5));
        Assertions.assertThat(booking).hasSize(3);
    }

    @Test
    void findBookingByBookerAndStateCurrent() {
        List<Booking> booking = bookingRepository.findBookingByBookerAndStateCurrent(2, PageRequest.of(0, 5));
        Assertions.assertThat(booking).hasSize(1);
    }

    @Test
    void findBookingByOwnerAndStateFuture() {
        List<Booking> booking = bookingRepository.findBookingByOwnerAndStateFuture(1, PageRequest.of(0, 5));
        Assertions.assertThat(booking).hasSize(1);
    }

    @Test
    void findBookingByOwnerAndStatePast() {
        List<Booking> booking = bookingRepository.findBookingByOwnerAndStatePast(1, PageRequest.of(0, 5));
        Assertions.assertThat(booking).hasSize(3);
    }

    @Test
    void findBookingByOwnerAndStateCurrent() {
        List<Booking> booking = bookingRepository.findBookingByOwnerAndStateCurrent(1, PageRequest.of(0, 5));
        Assertions.assertThat(booking).hasSize(1);
    }

    @Test
    void findNextBooking() {
        Booking booking = bookingRepository.findNextBooking(1);
        Assertions.assertThat(booking).hasFieldOrPropertyWithValue("id", 3);
    }

    @Test
    void findLastBooking() {
        Booking booking = bookingRepository.findLastBooking(1);
        Assertions.assertThat(booking).hasFieldOrPropertyWithValue("id", 5);
    }

    @Test
    void findApprovedBookingByPeriodAndItem() {
        List<Booking> booking = bookingRepository.findApprovedBookingByPeriodAndItem(
                new Booking(2, LocalDateTime.now().minusYears(1), LocalDateTime.now().plusYears(1),
                        new Item(1, null, null, null, null, null),
                        null, null));
        Assertions.assertThat(booking).hasSize(1);
    }
}