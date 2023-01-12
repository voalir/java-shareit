package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.exception.UserEmailEmployed;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserStorage userStorage;

    int i = 1;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        checkEmail(userDto.getEmail());
        user.setId(getId());
        return UserMapper.toUserDto(userStorage.add(user));
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        user.setId(userId);
        User currentUser = userStorage.get(user.getId());
        if (user.getName() == null) {
            user.setName(currentUser.getName());
        }
        if (user.getEmail() == null) {
            user.setEmail(currentUser.getEmail());
        } else if (!currentUser.getEmail().equals(user.getEmail())) {
            checkEmail(user.getEmail());
        }
        return UserMapper.toUserDto(userStorage.modify(user));
    }

    @Override
    public UserDto getUser(Integer userId) {
        return UserMapper.toUserDto(userStorage.get(userId));
    }

    @Override
    public void deleteUser(Integer userId) {
        userStorage.remove(userId);
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        return userStorage.getAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    private int getId() {
        return i++;
    }

    void checkEmail(String email) {
        if (email == null) {
            throw new ValidationException("email value is null");
        }
        if (userStorage.hasUserWithEmail(email)) {
            throw new UserEmailEmployed(String.format("email %s is busy", email));
        }
    }
}
