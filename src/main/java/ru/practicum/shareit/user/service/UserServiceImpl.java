package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        checkEmailByNull(user.getEmail());
        User savedUser = userRepository.save(user);
        log.info("saved user " + savedUser);
        return UserMapper.toUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) {
        User user = findById(userId);
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())) {
            user.setEmail(userDto.getEmail());
        }
        log.info("update user with id=" + userId);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto getUser(Integer userId) {
        return UserMapper.toUserDto(findById(userId));
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    void checkEmailByNull(String email) {
        if (email == null) {
            throw new ValidationException("email value is null");
        }
    }

    private User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("user with id=" + id + " not found"));
    }
}
