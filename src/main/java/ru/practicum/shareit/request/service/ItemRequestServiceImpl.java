package ru.practicum.shareit.request.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Override
    @Transactional
    public ItemRequestDto addItemRequest(Integer userId, ItemRequestDto itemRequestDto) {
        itemRequestDto.setRequestor(userService.getUser(userId));//checking user exist
        ItemRequest savedItemRequest = requestRepository.save(ItemRequestMapper.toItemRequest(itemRequestDto));
        return ItemRequestMapper.toItemRequestDto(savedItemRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemRequestDto> getAllRequests(Integer userId, Integer from, Integer size) {
        userService.getUser(userId);//checking user exist
        Collection<ItemRequest> itemRequests = requestRepository.findAllByRequestorIdNotOrderByCreatedDesc(userId,
                PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "created")));
        return getRequestsWithItems(itemRequests);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemRequestDto> getRequests(Integer userId) {
        userService.getUser(userId);//checking user exist
        Collection<ItemRequest> itemRequests = requestRepository.findAllByRequestorIdOrderByCreatedDesc(userId);
        return getRequestsWithItems(itemRequests);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemRequestDto getRequest(Integer id, Integer userId) {
        userService.getUser(userId);//checking user exist
        ItemRequest itemRequest = requestRepository.findById(id).orElseThrow(
                () -> new ItemRequestNotFoundException("request with id=" + id + " not found"));
        List<ItemDto> item = itemService.getItemByRequest(id);
        return ItemRequestMapper.toItemRequestDto(itemRequest, item);
    }

    private List<ItemRequestDto> getRequestsWithItems(Collection<ItemRequest> itemRequests) {
        Map<Integer, List<ItemDto>> items = itemService.getItemsByRequest(itemRequests.stream().map(ItemRequest::getId)
                .collect(Collectors.toList()));
        return itemRequests.stream().map(
                        itemRequest -> ItemRequestMapper.toItemRequestDto(itemRequest, items.get(itemRequest.getId())))
                .collect(Collectors.toList());
    }
}
