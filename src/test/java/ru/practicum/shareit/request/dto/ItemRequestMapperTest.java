package ru.practicum.shareit.request.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class ItemRequestMapperTest {

    @Test
    void toItemRequestDto() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ItemRequest itemRequest = getItemRequest(localDateTime);
        ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest);
        Assertions.assertThat(itemRequestDto)
                .hasFieldOrPropertyWithValue("id", itemRequest.getId())
                .hasFieldOrProperty("requestorId")
                .hasFieldOrProperty("items")
                .hasFieldOrPropertyWithValue("description", itemRequest.getDescription())
                .hasFieldOrPropertyWithValue("created", itemRequest.getCreated());
    }

    @Test
    void toItemRequest() {
        ItemRequestDto itemRequestDto = getItemRequestDto();
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto);
        Assertions.assertThat(itemRequest)
                .hasFieldOrPropertyWithValue("id", itemRequestDto.getId())
                .hasFieldOrProperty("requestor")
                .hasFieldOrPropertyWithValue("description", itemRequestDto.getDescription())
                .hasFieldOrProperty("created");
    }

    @Test
    void toItemRequestDtoWithItems() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ItemRequest itemRequest = getItemRequest(localDateTime);
        ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest, List.of());
        Assertions.assertThat(itemRequestDto)
                .hasFieldOrPropertyWithValue("id", itemRequest.getId())
                .hasFieldOrProperty("requestorId")
                .hasFieldOrProperty("items")
                .hasFieldOrPropertyWithValue("description", itemRequest.getDescription())
                .hasFieldOrPropertyWithValue("created", itemRequest.getCreated());
    }

    ItemRequestDto getItemRequestDto() {
        return new ItemRequestDto(1,
                "request",
                new UserDto(1, "user", "mail@mail.m"),
                null,
                new ArrayList<>());
    }

    ItemRequest getItemRequest(LocalDateTime localDateTime) {
        return new ItemRequest(1,
                "request",
                new User(1, "user", "mail@mail.m"),
                localDateTime);
    }
}