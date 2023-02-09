package ru.practicum.shareit.user.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.model.User;

class UserMapperTest {

    @Test
    void toUserDto() {
        UserDto userDto = new UserDto(0, "name", "mail@mail.m");

        User user = UserMapper.toUser(userDto);

        Assertions.assertThat(user)
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("email", "mail@mail.m");
    }

    @Test
    void toUser() {
        User user = new User(0, "name", "mail@mail.m");

        UserDto userDto = UserMapper.toUserDto(user);

        Assertions.assertThat(userDto)
                .hasFieldOrPropertyWithValue("id", 0)
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("email", "mail@mail.m");
    }
}