package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemDto itemDto;

    @PostMapping
    void addItem(@RequestHeader("X-Later-User-Id") int userId,
                 @RequestBody Item item) {

    }

    @PatchMapping("/{itemId}")
    void pathItem(@RequestHeader("X-Later-User-Id") int userId,
                  @PathVariable int itemId,
                  @RequestBody Item item) {

    }

    @GetMapping("/{itemId}")
    Item getItem(@PathVariable int itemId) {
        return itemDto.getItem(itemId);
    }

    @GetMapping
    List<Item> getItemsByOwner(@RequestHeader("X-Later-User-Id") int userId) {
        return itemDto.getItemsByOwner(userId);
    }
}
