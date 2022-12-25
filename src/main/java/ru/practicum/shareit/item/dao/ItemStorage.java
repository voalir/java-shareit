package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {

    Item add(Item item);

    Item modify(Item item);

    Item get(int id);

    List<Item> getAllByOwner(int userId);

    List<Item> foundItemsByStringRequest(String text);
}
