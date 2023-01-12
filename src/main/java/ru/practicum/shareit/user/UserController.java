package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    UserDto addUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PatchMapping("/{userId}")
    UserDto pathUser(@PathVariable Integer userId, @Valid @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userDto);
    }

    @GetMapping("/{userId}")
    UserDto getUser(@PathVariable Integer userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    Collection<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }
}
