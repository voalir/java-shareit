package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.exception.UserAccessException;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserService userService;

    @Override
    public ItemDto addItem(Integer userId, ItemDto itemDto) {
        User user = UserMapper.toUser(userService.getUser(userId));
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(user);
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto updateItem(Integer userId, int itemId, ItemDto itemDto) {
        Item itemCurrent = findById(itemId);
        Item item = getItemToUpdate(itemCurrent, itemDto);
        User user = UserMapper.toUser(userService.getUser(userId));
        if (itemCurrent.getOwner().equals(user)) {
            item.setOwner(user);
            item.setId(itemCurrent.getId());
            return ItemMapper.toItemDto(itemRepository.save(item));
        } else {
            throw new UserAccessException(String.format("user with id=%s not owner for item with id=%s", userId, itemId));
        }
    }

    @Override
    public ItemDto getItem(int itemId) {
        return ItemMapper.toItemDto(findById(itemId));
    }

    @Override
    public Item getRawItem(int itemId) {
        return findById(itemId);
    }

    @Override
    public List<ItemDto> getItemsByOwner(Integer userId) {
        return itemRepository.findByOwner_id(userId).stream().map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> foundItems(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.findItems(text).stream().map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
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

    private Item findById(Integer id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("item with id=" + id + " not found"));
    }
}
