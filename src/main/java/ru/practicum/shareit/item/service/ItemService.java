package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto addItem(int userId, ItemDto itemDto);

    ItemDto pathItem(int userId, int itemId, ItemDto itemDto);

    ItemDto getItem(int itemId);

    List<ItemDto> getItemsByOwner(int userId);

    List<ItemDto> foundItems(String text);
}
