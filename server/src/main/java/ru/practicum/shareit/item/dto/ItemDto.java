package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.dto.ShortBookingDto;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemDto {

    private final Integer id;
    private final String name;
    private final String description;
    private final Boolean available;
    private final Integer requestId;
    private final ShortBookingDto nextBooking;
    private final ShortBookingDto lastBooking;
    private final List<CommentDto> comments;
}
