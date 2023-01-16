package ru.practicum.shareit.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.exception.BookingAccessException;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.Objects;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserService userService;
    @Autowired
    ItemService itemService;

    @Override
    public BookingDto addBooking(BookingDto bookingDto, Integer userId) {
        Booking savedBooker = bookingRepository.save(prepareBookingToSave(bookingDto, userId));
        log.info("saved booking: " + savedBooker);
        return BookingMapper.toBookingDto(savedBooker);
    }

    @Override
    public BookingDto confirmBooking(Integer bookingId, Integer userId, Boolean approved) {
        Booking booking = findById(bookingId);
        if (!booking.getBooker().getId().equals(userId)) {
            throw new BookingAccessException(
                    String.format("user with id=%s does not have access to the booking with id=%s", userId, bookingId));
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        log.info("save booking: " + booking);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public Collection<BookingDto> getBookingByOwner(Integer userId, BookingState state) {
        if (state == BookingState.ALL) {
            return bookingRepository.getBookingByOwner(userId);
        } else {
            return bookingRepository.getBookingByOwnerAndState(userId, state);
        }

    }

    @Override
    public Collection<BookingDto> getBookingByBooker(Integer userId, BookingState state) {
        if (state == BookingState.ALL) {
            return bookingRepository.getBookingByBooker(userId);
        } else {
            return bookingRepository.getBookingByBookerAndState(userId, state);
        }
    }

    @Override
    public BookingDto getBookingById(Integer userId, Integer bookingId) {
        Booking booking = findById(bookingId);
        if (!Objects.equals(booking.getBooker().getId(), bookingId)
                || !Objects.equals(booking.getItem().getOwner().getId(), bookingId)) {
            throw new BookingAccessException(
                    String.format("user with id=%s does not have access to the booking with id=%s", userId, bookingId));
        }
        return BookingMapper.toBookingDto(booking);
    }

    private Booking prepareBookingToSave(BookingDto bookingDto, Integer userId) {
        bookingDto.setItem(itemService.getItem(bookingDto.getItemId()));
        bookingDto.setBooker(userService.getUser(userId));
        Booking booking = BookingMapper.toBooking(bookingDto);
        booking.setStatus(BookingStatus.WAITING);
        return booking;
    }

    private Booking findById(Integer id) {
        return bookingRepository.findById(id).orElseThrow(
                () -> new BookingNotFoundException("booking with id=" + id + " not found"));
    }
}
