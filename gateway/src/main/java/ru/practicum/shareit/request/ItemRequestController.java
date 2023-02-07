package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/requests")
@Validated
public class ItemRequestController {

    @Autowired
    ItemRequestClient itemRequestClient;

    @PostMapping
    ResponseEntity<Object> addRequest(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                      @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestClient.addItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    ResponseEntity<Object> getRequests(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemRequestClient.getRequests(userId);
    }

    @GetMapping("/{requestId}")
    ResponseEntity<Object> getRequest(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                      @PathVariable("requestId") Integer itemRequestId) {
        return itemRequestClient.getRequest(itemRequestId, userId);
    }

    @GetMapping("/all")
    ResponseEntity<Object> getAllRequests(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                          @RequestParam(defaultValue = "10") @Positive Integer size) {
        return itemRequestClient.getAllRequests(userId, from, size);
    }

}
