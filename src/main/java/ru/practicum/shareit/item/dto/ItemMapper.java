package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.booking.dto.ShortBookingDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

public final class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : null,
                null,
                null,
                new ArrayList<>()
        );
    }

    public static ItemDto toItemDto(Item item, ShortBookingDto nextBooking, ShortBookingDto lastBooking, List<CommentDto> comments) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : null,
                nextBooking,
                lastBooking,
                comments
        );
    }

    public static Item toItem(ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                null,
                null
        );
    }
}
