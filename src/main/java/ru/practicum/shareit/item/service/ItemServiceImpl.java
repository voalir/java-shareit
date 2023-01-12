package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemStorage;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.exception.UserAccessException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemStorage itemStorage;
    @Autowired
    UserStorage userStorage;

    int i = 1;

    @Override
    public ItemDto addItem(int userId, ItemDto itemDto) {
        User user = userStorage.get(userId);
        Item item = ItemMapper.toItem(itemDto);
        item.setId(getId());
        item.setOwner(user);
        return ItemMapper.toItemDto(itemStorage.add(item));
    }

    @Override
    public ItemDto updateItem(int userId, int itemId, ItemDto itemDto) {
        Item itemCurrent = itemStorage.get(itemId);
        Item item = getItemToUpdate(itemCurrent, itemDto);
        User user = userStorage.get(userId);
        if (itemCurrent.getOwner().equals(user)) {
            item.setOwner(user);
            item.setId(itemCurrent.getId());
            return ItemMapper.toItemDto(itemStorage.modify(item));
        } else {
            throw new UserAccessException(String.format("user with id=%s not owner for item with id=%s", userId, itemId));
        }
    }

    @Override
    public ItemDto getItem(int itemId) {
        return ItemMapper.toItemDto(itemStorage.get(itemId));
    }

    @Override
    public List<ItemDto> getItemsByOwner(int userId) {
        return itemStorage.getAllByOwner(userId).stream().map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> foundItems(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.findItems(text).stream().map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    private int getId() {
        return i++;
    }

    private Item getItemToUpdate(Item itemCurrent, ItemDto itemDto) {
        Item item = ItemMapper.toItem(itemDto);
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            item.setDescription(itemCurrent.getDescription());
        }
        if (item.getName() == null || item.getName().isBlank()) {
            item.setName(itemCurrent.getName());
        }
        if (itemDto.getAvailable() == null) {
            item.setAvailable(itemCurrent.getAvailable());
        }
        return item;
    }
}
