package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping
    void addItem(@RequestHeader("X-Later-User-Id") int userId,
                 @RequestBody ItemDto itemDto) {
        itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    void pathItem(@RequestHeader("X-Later-User-Id") int userId,
                  @PathVariable int itemId,
                  @RequestBody ItemDto itemDto) {
        itemService.pathItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    ItemDto getItem(@PathVariable int itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    List<ItemDto> getItemsByOwner(@RequestHeader("X-Later-User-Id") int userId) {
        return itemService.getItemsByOwner(userId);
    }

    @GetMapping("/search")
    List<ItemDto> searchItems(@RequestParam String text) {
        return itemService.foundItems(text);
    }
}
