package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;

import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("select b from Booking as b where b.item.owner.id = ?1 order by start desc")
    Collection<Booking> findBookingByOwner(Integer userId);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and cast(b.status as text) = ?2 order by start desc")
    Collection<Booking> findBookingByOwnerAndState(Integer userId, String state);

    @Query("select b from Booking as b where b.booker.id = ?1 order by start desc")
    Collection<Booking> findBookingByBooker(Integer userId);

    @Query("select b from Booking as b where b.booker.id = ?1 and b.item.id = ?2 and b.end < current_timestamp order by start desc")
    Collection<Booking> findBookingByBookerAndItem(Integer userId, Integer itemId);

    @Query("select b from Booking as b where b.booker.id = ?1 and cast(b.status as text) = ?2 order by start desc")
    Collection<Booking> findBookingByBookerAndState(Integer userId, String state);

    @Query("select b from Booking as b where b.booker.id = ?1 and b.start > current_timestamp order by start desc")
    Collection<Booking> findBookingByBookerAndStateFuture(Integer userId);

    @Query("select b from Booking as b where b.booker.id = ?1 and b.end < current_timestamp order by start desc")
    Collection<Booking> findBookingByBookerAndStatePast(Integer userId);

    @Query("select b from Booking as b where b.booker.id = ?1 and current_timestamp between b.start and b.end")
    Collection<Booking> findBookingByBookerAndStateCurrent(Integer userId);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and b.start > current_timestamp order by start desc")
    Collection<Booking> findBookingByOwnerAndStateFuture(Integer userId);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and b.end < current_timestamp order by start desc")
    Collection<Booking> findBookingByOwnerAndStatePast(Integer userId);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and current_timestamp between b.start and b.end")
    Collection<Booking> findBookingByOwnerAndStateCurrent(Integer userId);

    @Query("select b from Booking as b where b.item.id = ?1 and b.start = " +
            "(select min(b.start) from Booking as b where b.item.id = ?1 and b.start > current_timestamp)" +
            "")
    Booking findNextBooking(Integer itemId);

    @Query("select b from Booking as b where b.item.id = ?1 and b.end = " +
            "(select max(b.end) from Booking as b where b.item.id = ?1 and b.end < current_timestamp)")
    Booking findLastBooking(Integer itemId);

}
