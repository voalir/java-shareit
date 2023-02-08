package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    @Autowired
    ItemRequestService itemRequestService;

    @PostMapping
    ItemRequestDto addRequest(@RequestHeader("X-Sharer-User-Id") Integer userId,
                              @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestService.addItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    Collection<ItemRequestDto> getRequests(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemRequestService.getRequests(userId);
    }

    @GetMapping("/{requestId}")
    ItemRequestDto getRequest(@RequestHeader("X-Sharer-User-Id") Integer userId,
                              @PathVariable("requestId") Integer itemRequestId) {
        return itemRequestService.getRequest(itemRequestId, userId);
    }

    @GetMapping("/all")
    Collection<ItemRequestDto> getAllRequests(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                              @RequestParam(defaultValue = "0") Integer from,
                                              @RequestParam(defaultValue = "10") Integer size) {
        return itemRequestService.getAllRequests(userId, from, size);
    }

}
