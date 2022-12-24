package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.ObjectStorage;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage extends ObjectStorage<Item> {
    List<Item> getAllByOwner(int userId);

    List<Item> foundItemsByStringRequest(String text);
}
