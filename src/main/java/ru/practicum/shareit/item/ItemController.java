package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
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
    ItemDto addItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                    @Valid @RequestBody ItemDto itemDto) {
        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    ItemDto pathItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                     @PathVariable Integer itemId,
                     @RequestBody ItemDto itemDto) {
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    ItemDto getItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                    @PathVariable Integer itemId) {
        return itemService.getItem(itemId, userId);
    }

    @GetMapping
    List<ItemDto> getItemsByOwner(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.getItemsByOwner(userId);
    }

    @GetMapping("/search")
    List<ItemDto> searchItems(@RequestParam String text) {
        return itemService.foundItems(text);
    }

    @PostMapping("/{itemId}/comment")
    CommentDto addComment(@RequestHeader("X-Sharer-User-Id") Integer userId,
                          @PathVariable Integer itemId,
                          @Valid @RequestBody CommentDto commentDto) {
        return itemService.addComment(userId, itemId, commentDto);
    }
}
