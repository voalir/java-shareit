package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("select b from Booking as b where b.item.owner.id = ?1 order by start desc")
    Page<Booking> findBookingByOwner(Integer userId, Pageable pageable);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and cast(b.status as text) = ?2 order by start desc")
    List<Booking> findBookingByOwnerAndState(Integer userId, String state, Pageable pageable);

    @Query("select b from Booking as b where b.booker.id = ?1 order by start desc")
    List<Booking> findBookingByBooker(Integer userId, Pageable pageable);

    @Query("select b from Booking as b where b.booker.id = ?1 and b.item.id = ?2 and b.end < current_timestamp order by start desc")
    List<Booking> findBookingByBookerAndItem(Integer userId, Integer itemId, Pageable pageable);

    @Query("select b from Booking as b where b.booker.id = ?1 and cast(b.status as text) = ?2 order by start desc")
    List<Booking> findBookingByBookerAndState(Integer userId, String state, Pageable pageable);

    @Query("select b from Booking as b where b.booker.id = ?1 and b.start > current_timestamp order by start desc")
    List<Booking> findBookingByBookerAndStateFuture(Integer userId, Pageable pageable);

    @Query("select b from Booking as b where b.booker.id = ?1 and b.end < current_timestamp order by start desc")
    List<Booking> findBookingByBookerAndStatePast(Integer userId, Pageable pageable);

    @Query("select b from Booking as b where b.booker.id = ?1 and current_timestamp between b.start and b.end")
    List<Booking> findBookingByBookerAndStateCurrent(Integer userId, Pageable pageable);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and b.start > current_timestamp order by start desc")
    List<Booking> findBookingByOwnerAndStateFuture(Integer userId, Pageable pageable);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and b.end < current_timestamp order by start desc")
    List<Booking> findBookingByOwnerAndStatePast(Integer userId, Pageable pageable);

    @Query("select b from Booking as b where b.item.owner.id = ?1 and current_timestamp between b.start and b.end")
    List<Booking> findBookingByOwnerAndStateCurrent(Integer userId, Pageable pageable);

    @Query("select b from Booking as b where b.item.id = ?1 and b.start = " +
            "(select min(b.start) from Booking as b where b.item.id = ?1 and b.start > current_timestamp)")
    Booking findNextBooking(Integer itemId);

    @Query("select b from Booking as b where b.item.id = ?1 and b.end = " +
            "(select max(b.end) from Booking as b where b.item.id = ?1 and b.end < current_timestamp)")
    Booking findLastBooking(Integer itemId);

    @Query("select b from Booking as b where b.item.id = :#{#booking.item.id} " +
            "and b.start<:#{#booking.end} and b.end>:#{#booking.start} " +
            "and status = ru.practicum.shareit.booking.model.BookingStatus.APPROVED")
    List<Booking> findApprovedBookingByPeriodAndItem(@Param("booking") Booking booking);
}
