package ru.practicum.shareit.item.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.ShortBookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

class ItemMapperTest {

    @Test
    void toItemDto() {
        Item item = getItem();
        ItemDto itemDto = ItemMapper.toItemDto(item);
        Assertions.assertThat(itemDto)
                .hasFieldOrPropertyWithValue("id", item.getId())
                .hasFieldOrPropertyWithValue("name", item.getName())
                .hasFieldOrPropertyWithValue("description", item.getDescription())
                .hasFieldOrPropertyWithValue("available", item.getAvailable())
                .hasFieldOrPropertyWithValue("requestId", item.getRequest().getId())
                .hasFieldOrProperty("comments");
    }

    @Test
    void toItem() {
        ItemDto itemDto = getItemDto();
        Item item = ItemMapper.toItem(itemDto);
        Assertions.assertThat(item)
                .hasFieldOrPropertyWithValue("id", itemDto.getId())
                .hasFieldOrPropertyWithValue("name", itemDto.getName())
                .hasFieldOrPropertyWithValue("description", itemDto.getDescription())
                .hasFieldOrPropertyWithValue("available", itemDto.getAvailable());
    }

    ItemDto getItemDto() {
        return new ItemDto(
                1,
                "name item",
                "description",
                true,
                1,
                new ShortBookingDto(1, 1),
                new ShortBookingDto(3, 3),
                new ArrayList<>()
        );
    }

    Item getItem() {
        return new Item(
                1,
                "name item",
                "description",
                true,
                new User(1, "user", "m@m.m"),
                new ItemRequest(1, "request", new User(2, "user1", "m1@m.m"), LocalDateTime.now())
        );
    }
}