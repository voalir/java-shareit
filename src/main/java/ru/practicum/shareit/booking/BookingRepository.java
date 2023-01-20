package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("select b from Booking as b where b.item.owner.id = ?1 order by start desc")
    Collection<Booking> getBookingByOwner(Integer userId);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and b.status = ?2 order by start desc")
    Collection<Booking> getBookingByOwnerAndState(Integer userId, BookingState state);

    @Query("select b from Booking as b where b.booker.id = ?1 order by start desc")
    Collection<Booking> getBookingByBooker(Integer userId);

    @Query("select b from Booking as b where b.booker.id = ?1 and b.status = ?2 order by start desc")
    Collection<Booking> getBookingByBookerAndState(Integer userId, BookingState state);

    @Query("select b from Booking as b where b.booker.id = ?1 and b.start > current_timestamp order by start desc")
    Collection<Booking> getBookingByBookerAndStateFuture(Integer userId);

    @Query("select b from Booking as b where b.booker.id = ?1 and b.end < current_timestamp order by start desc")
    Collection<Booking> getBookingByBookerAndStatePast(Integer userId);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and b.start > current_timestamp order by start desc")
    Collection<Booking> getBookingByOwnerAndStateFuture(Integer userId);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and b.end < current_timestamp order by start desc")
    Collection<Booking> getBookingByOwnerAndStatePast(Integer userId);
}
