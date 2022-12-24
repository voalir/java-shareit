package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto addUser(UserDto userDto);

    UserDto updateUser(Integer userId, UserDto userDto);

    UserDto getUser(Integer userId);

    void deleteUser(Integer userId);
}
