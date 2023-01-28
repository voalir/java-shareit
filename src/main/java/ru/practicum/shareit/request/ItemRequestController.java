package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.Valid;
import java.util.Collection;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    @Autowired
    ItemRequestService itemRequestService;

    @PostMapping
    ItemRequestDto addRequest(@RequestHeader("X-Sharer-User-Id") Integer userId, @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestService.addItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    Collection<ItemRequestDto> getRequests(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemRequestService.getRequests(userId);
    }

    @GetMapping
    ItemRequestDto getRequest(@PathVariable("requestId") Integer itemRequestId) {
        return itemRequestService.getRequest(itemRequestId);
    }

    @GetMapping("/all")
    Collection<ItemRequestDto> getAllRequests(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                              @PathVariable("from") Integer from,
                                              @PathVariable("size") Integer size) {
        return itemRequestService.getAllRequests(userId, from, size);
    }

}
