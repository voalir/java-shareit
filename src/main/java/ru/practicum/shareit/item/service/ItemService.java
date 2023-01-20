package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    ItemDto addItem(Integer userId, ItemDto itemDto);

    ItemDto updateItem(Integer userId, int itemId, ItemDto itemDto);

    ItemDto getItem(int itemId);

    Item getRawItem(int itemId);

    List<ItemDto> getItemsByOwner(Integer userId);

    List<ItemDto> foundItems(String text);
}
