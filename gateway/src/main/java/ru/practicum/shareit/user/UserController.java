package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserClient userClient;

    @PostMapping
    ResponseEntity<Object> addUser(@Validated(CreateValidationGroup.class) @RequestBody UserDto userDto) {
        return userClient.addUser(userDto);
    }

    @PatchMapping("/{userId}")
    ResponseEntity<Object> pathUser(@PathVariable Integer userId, @Validated(UpdateValidationGroup.class) @RequestBody UserDto userDto) {
        return userClient.updateUser(userId, userDto);
    }

    @GetMapping("/{userId}")
    ResponseEntity<Object> getUser(@PathVariable Integer userId) {
        return userClient.getUser(userId);
    }

    @GetMapping
    ResponseEntity<Object> getAllUsers() {
        return userClient.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable Integer userId) {
        userClient.deleteUser(userId);
    }
}
