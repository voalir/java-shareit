package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("select b from Booking as b where b.item.owner.id = ?1")
    Collection<BookingDto> getBookingByOwner(Integer userId);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and b.status = ?2")
    Collection<BookingDto> getBookingByOwnerAndState(Integer userId, BookingState state);

    @Query("select b from Booking as b where b.booker.id = ?1")
    Collection<BookingDto> getBookingByBooker(Integer userId);

    @Query("select b from Booking as b where b.booker.id = ?1 and b.status = ?2")
    Collection<BookingDto> getBookingByBookerAndState(Integer userId, BookingState state);
}
