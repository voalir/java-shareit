package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public void addItem(int userId, ItemDto itemDto) {

    }

    @Override
    public void pathItem(int userId, int itemId, ItemDto itemDto) {

    }

    @Override
    public ItemDto getItem(int itemId) {
        return null;
    }

    @Override
    public List<ItemDto> getItemsByOwner(int userId) {
        return null;
    }

    @Override
    public List<ItemDto> foundItems(String text) {
        return null;
    }
}
