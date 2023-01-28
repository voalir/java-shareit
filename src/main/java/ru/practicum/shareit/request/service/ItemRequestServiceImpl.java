package ru.practicum.shareit.request.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserService userService;

    @Override
    public ItemRequestDto addItemRequest(Integer userId, ItemRequestDto itemRequestDto) {
        userService.getUser(userId);//checking user exist
        ItemRequest savedItemRequest = requestRepository.save(ItemRequestMapper.toItemRequest(itemRequestDto));
        return ItemRequestMapper.toItemRequestDto(savedItemRequest);
    }

    @Override
    public Collection<ItemRequestDto> getAllRequests(Integer userId, Integer from, Integer size) {
        return requestRepository.findAllByRequestorIdNotOrderByCreatedDesc(userId,
                        PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "created")))
                .stream().map(ItemRequestMapper::toItemRequestDto).collect(Collectors.toList());
    }

    @Override
    public Collection<ItemRequestDto> getRequests(Integer userId) {
        return requestRepository.findAllByRequestorIdOrderByCreatedDesc(userId)
                .stream().map(ItemRequestMapper::toItemRequestDto).collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getRequest(Integer id) {
        return ItemRequestMapper.toItemRequestDto(requestRepository.findById(id).orElseThrow(
                () -> new ItemRequestNotFoundException("request with id=" + id + " not found")));
    }
}
