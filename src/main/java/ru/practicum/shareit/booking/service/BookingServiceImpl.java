package ru.practicum.shareit.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.exception.BookingAccessException;
import ru.practicum.shareit.booking.exception.BookingCheckException;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.exception.BookingUnsupportedStatusException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.exception.ItemAvailableException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Transactional
    public BookingDto addBooking(BookingDto bookingDto, Integer userId) {
        Booking savedBooker = bookingRepository.save(prepareBookingToSave(bookingDto, userId));
        log.info("saved booking: " + savedBooker);
        return BookingMapper.toBookingDto(savedBooker);
    }

    @Override
    @Transactional
    public BookingDto confirmBooking(Integer bookingId, Integer userId, Boolean approved) {
        Booking booking = findById(bookingId);
        checkConfirmBooking(bookingId, userId, booking, approved);
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        log.info("save booking: " + booking);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BookingDto> getBookingByOwner(Integer userId, String state, Integer from, Integer size) {
        BookingState bookingState;
        try {
            bookingState = BookingState.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new BookingUnsupportedStatusException(state);
        }
        userService.getUser(userId);//checking user exist
        PageRequest pageRequest = PageRequest.of(from / size, size);
        switch (bookingState) {
            case ALL:
                return bookingRepository.findBookingByOwner(userId, pageRequest).stream().map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case PAST:
                return bookingRepository.findBookingByOwnerAndStatePast(userId, pageRequest).stream().map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingRepository.findBookingByOwnerAndStateFuture(userId, pageRequest).stream().map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case CURRENT:
                return bookingRepository.findBookingByOwnerAndStateCurrent(userId, pageRequest).stream().map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            default:
                return bookingRepository.findBookingByOwnerAndState(userId, state, pageRequest).stream().map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BookingDto> getBookingByBooker(Integer userId, String state, Integer from, Integer size) {
        BookingState bookingState;
        try {
            bookingState = BookingState.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new BookingUnsupportedStatusException(state);
        }
        userService.getUser(userId);//checking user exist
        PageRequest pageRequest = PageRequest.of(from / size, size);
        switch (bookingState) {
            case ALL:
                return bookingRepository.findBookingByBooker(userId, pageRequest).stream().map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case PAST:
                return bookingRepository.findBookingByBookerAndStatePast(userId, pageRequest).stream().map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingRepository.findBookingByBookerAndStateFuture(userId, pageRequest).stream().map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case CURRENT:
                return bookingRepository.findBookingByBookerAndStateCurrent(userId, pageRequest).stream().map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            default:
                return bookingRepository.findBookingByBookerAndState(userId, state, pageRequest).stream().map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto getBookingById(Integer userId, Integer bookingId) {
        userService.getUser(userId);//checking user exist
        Booking booking = findById(bookingId);
        if (!Objects.equals(booking.getBooker().getId(), userId)
                && !Objects.equals(booking.getItem().getOwner().getId(), userId)) {
            throw new BookingAccessException(
                    String.format("user with id=%s does not have access to the booking with id=%s", userId, bookingId));
        }
        return BookingMapper.toBookingDto(booking);
    }

    private Booking prepareBookingToSave(BookingDto bookingDto, Integer userId) {
        checkBookingDtoInterval(bookingDto);
        Item item = itemService.getRawItem(bookingDto.getItemId());
        if (!item.getAvailable()) {
            throw new ItemAvailableException("item with id=" + bookingDto.getItemId() + " is not available");
        }
        if (item.getOwner().getId().equals(userId)) {
            throw new ItemNotFoundException("item with id=" + bookingDto.getItemId() + " is not available to owner");
        }
        bookingDto.setItem(ItemMapper.toItemDto(item));
        bookingDto.setBooker(userService.getUser(userId));
        Booking booking = BookingMapper.toBooking(bookingDto);
        booking.setStatus(BookingStatus.WAITING);
        return booking;
    }

    private void checkBookingDtoInterval(BookingDto bookingDto) {
        if (bookingDto.getStart().isAfter(bookingDto.getEnd())) {
            throw new BookingCheckException("date start after date end");
        }
    }

    private Booking findById(Integer id) {
        return bookingRepository.findById(id).orElseThrow(
                () -> new BookingNotFoundException("booking with id=" + id + " not found"));
    }

    private void checkConfirmBooking(Integer bookingId, Integer userId, Booking booking, Boolean approved) {
        if (booking.getStatus() != BookingStatus.WAITING) {
            throw new BookingCheckException(
                    String.format("booking with id=%s finished", bookingId));
        }
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new BookingAccessException(
                    String.format("user with id=%s does not have access to the booking item with id=%s",
                            userId, booking.getItem().getId()));
        }
        if (approved && bookingRepository.findApprovedBookingByPeriodAndItem(booking).size() > 0) {
            throw new BookingAccessException(
                    String.format("user with id=%s does not have access to the booking item with id=%s. " +
                            "This item is busy for this time", userId, booking.getItem().getId()));
        }
    }
}
