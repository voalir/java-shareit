package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Map;

public interface ItemService {

    ItemDto addItem(Integer userId, ItemDto itemDto);

    ItemDto updateItem(Integer userId, Integer itemId, ItemDto itemDto);

    ItemDto getItem(Integer itemId, Integer userId);

    Item getRawItem(Integer itemId);

    List<ItemDto> getItemsByOwner(Integer userId, Integer from, Integer size);

    List<ItemDto> foundItems(String text, Integer from, Integer size);

    CommentDto addComment(Integer userId, Integer itemId, CommentDto commentDto);

    Map<Integer, List<ItemDto>> getItemsByRequest(List<Integer> id);

    List<ItemDto> getItemByRequest(Integer id);
}
