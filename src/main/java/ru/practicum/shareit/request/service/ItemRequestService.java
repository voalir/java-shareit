package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Collection;

public interface ItemRequestService {

    ItemRequestDto addItemRequest(Integer userId, ItemRequestDto itemRequestDto);

    Collection<ItemRequestDto> getAllRequests(Integer userId, Integer from, Integer size);

    Collection<ItemRequestDto> getRequests(Integer userId);

    ItemRequestDto getRequest(Integer itemRequestId, Integer userId);
}
