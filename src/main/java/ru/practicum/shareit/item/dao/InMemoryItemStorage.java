package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.InMemoryObjectStorage;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryItemStorage extends InMemoryObjectStorage<Item> implements ItemStorage {

    @Override
    public Item add(Item item) {
        super.putObject(item.getId(), item);
        return super.get(item.getId());
    }

    @Override
    public Item get(int id) {
        if (super.containsObjectKey(id)) {
            return super.get(id);
        } else {
            throw new ItemNotFoundException(String.format("item with id=%s not found", id));
        }
    }

    @Override
    public Item modify(Item item) {
        super.putObject(item.getId(), item);
        return super.get(item.getId());
    }

    @Override
    public List<Item> getAllByOwner(int userId) {
        return super.getAll().stream().filter(s -> s.getOwner().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> foundItemsByStringRequest(String text) {
        return super.getAll().stream()
                .filter(Item::getAvailable)
                .filter(s -> s.getDescription().toLowerCase().contains(text.toLowerCase())
                        || s.getName().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }
}
