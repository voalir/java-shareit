package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
class UserDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void userDtoTest() throws JsonProcessingException {
        UserDto userDto = new UserDto(0, "name", "mail@mail.m");
        String expectedResult = "{\"id\":0,\"name\":\"name\",\"email\":\"mail@mail.m\"}";
        assertEquals(expectedResult, objectMapper.writeValueAsString(userDto));
        assertDoesNotThrow(() -> objectMapper.readValue(expectedResult, UserDto.class));
    }
}