package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Deprecated
public interface ItemStorage {

    Item add(Item item);

    Item modify(Item item);

    Item get(Integer id);

    List<Item> getAllByOwner(Integer userId);

    List<Item> findItems(String text);
}
