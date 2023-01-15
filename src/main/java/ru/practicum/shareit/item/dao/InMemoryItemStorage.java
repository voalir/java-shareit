package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Deprecated
public class InMemoryItemStorage implements ItemStorage {

    private final Map<Integer, Item> items = new HashMap<>();

    @Override
    public Item add(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item get(Integer id) {
        if (items.containsKey(id)) {
            return items.get(id);
        } else {
            throw new ItemNotFoundException(String.format("item with id=%s not found", id));
        }
    }

    @Override
    public Item modify(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public List<Item> getAllByOwner(Integer userId) {
        return items.values().stream().filter(s -> s.getOwner().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> findItems(String text) {
        return items.values().stream()
                .filter(Item::getAvailable)
                .filter(s -> s.getDescription().toLowerCase().contains(text.toLowerCase())
                        || s.getName().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }

}
