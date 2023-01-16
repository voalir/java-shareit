package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Date;

@Data
@AllArgsConstructor
public class BookingDto {

    private Integer id;
    private Date start;
    private Date end;
    private Integer itemId;
    private ItemDto item;
    private UserDto booker;
    private BookingStatus status;
}
