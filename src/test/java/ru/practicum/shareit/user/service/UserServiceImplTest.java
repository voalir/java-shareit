package ru.practicum.shareit.user.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

//@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    @Order(0)
    void addUser() {
        UserDto userDto = new UserDto(null, "name", "mail@mail.m");
        UserDto userDto2 = new UserDto(null, "name2", "mail2@mail.m");
        Optional<UserDto> createdUser = Optional.of(userService.addUser(userDto));
        Assertions.assertThat(createdUser)
                .isPresent()
                .hasValueSatisfying(f -> {
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("id", 1);
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("name", "name");
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("email", "mail@mail.m");
                        }
                );
        Optional<UserDto> createdUser2 = Optional.of(userService.addUser(userDto2));
        Assertions.assertThat(createdUser2)
                .isPresent()
                .hasValueSatisfying(f -> {
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("id", 2);
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("name", "name2");
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("email", "mail2@mail.m");
                        }
                );
        assertThrows(DataIntegrityViolationException.class, () -> userService.addUser(userDto));
    }

    @Test
    @Order(1)
    void updateUser() {
        UserDto userDto = new UserDto(null, "nameNew", "mail@mail.m");
        Optional<UserDto> createdUser = Optional.of(userService.updateUser(1, userDto));
        Assertions.assertThat(createdUser)
                .isPresent()
                .hasValueSatisfying(f -> {
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("id", 1);
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("name", "nameNew");
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("email", "mail@mail.m");
                        }
                );
        assertThrows(DataIntegrityViolationException.class, () -> userService.updateUser(2, userDto));
    }

    @Test
    @Order(2)
    void getUser() {
        Optional<UserDto> createdUser = Optional.of(userService.getUser(1));
        Assertions.assertThat(createdUser)
                .isPresent()
                .hasValueSatisfying(f -> {
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("id", 1);
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("name", "nameNew");
                            Assertions.assertThat(f).hasFieldOrPropertyWithValue("email", "mail@mail.m");
                        }
                );
    }

    @Test
    @Order(4)
    void deleteUser() {
        int countUsers = userService.getAllUsers().size();
        userService.deleteUser(1);
        Assertions.assertThat(userService.getAllUsers()).hasSize(countUsers - 1);
    }

    @Test
    @Order(3)
    void getAllUsers() {
        Collection<UserDto> users = userService.getAllUsers();
        Assertions.assertThat(users).hasSize(2).map(UserDto::getId).contains(1, 2);
    }

    @Test
    void checkEmailByNull() {
        UserDto userDto = new UserDto(null, "nameNew", null);
        assertThrows(ValidationException.class, () -> userService.addUser(userDto));
    }
}