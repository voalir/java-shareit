package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@RestController
@RequestMapping("/items")
@Validated
public class ItemController {

    @Autowired
    ItemClient itemClient;

    @PostMapping
    ResponseEntity<Object> addItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                   @Valid @RequestBody ItemDto itemDto) {
        return itemClient.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    ResponseEntity<Object> pathItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                    @PathVariable Integer itemId,
                                    @RequestBody ItemDto itemDto) {
        return itemClient.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    ResponseEntity<Object> getItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                   @PathVariable Integer itemId) {
        return itemClient.getItem(itemId, userId);
    }

    @GetMapping
    ResponseEntity<Object> getItemsByOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        return itemClient.getItemsByOwner(userId, from, size);
    }

    @GetMapping("/search")
    ResponseEntity<Object> searchItems(@RequestParam String text,
                                       @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                       @RequestParam(defaultValue = "10") @Positive Integer size) {
        return itemClient.foundItems(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                      @PathVariable Integer itemId,
                                      @Valid @RequestBody CommentDto commentDto) {
        return itemClient.addComment(userId, itemId, commentDto);
    }
}
