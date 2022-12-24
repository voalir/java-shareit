package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.exception.UserEmailEmployed;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserStorage userStorage;

    int i = 1;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = UserDto.toUser(userDto);
        checkEmail(userDto.getEmail());
        user.setId(getId());
        userStorage.addUser(user);
        return UserDto.toUserDto(user);
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) {
        User user = UserDto.toUser(userDto);
        user.setId(userId);
        User currentUser = userStorage.getUser(user.getId());
        if (user.getName() == null) {
            user.setName(currentUser.getName());
        }
        if (user.getEmail() == null) {
            user.setEmail(currentUser.getEmail());
        } else if (!currentUser.getEmail().equals(user.getEmail())) {
            checkEmail(user.getEmail());
        }
        userStorage.updateUser(user);
        return UserDto.toUserDto(user);
    }

    @Override
    public UserDto getUser(Integer userId) {
        return UserDto.toUserDto(userStorage.getUser(userId));
    }

    @Override
    public void deleteUser(Integer userId) {
        userStorage.deleteUser(userId);
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
