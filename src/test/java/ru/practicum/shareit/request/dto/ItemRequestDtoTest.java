package ru.practicum.shareit.request.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@JsonTest
class ItemRequestDtoTest {
    @Autowired
    private JacksonTester<ItemRequestDto> json;

    @Test
    void bookingDtoTest() throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();
        ItemRequestDto itemRequestDto = getItemRequestDto(localDateTime);
        Optional<JsonContent<ItemRequestDto>> result = Optional.of(json.write(itemRequestDto));

        Assertions.assertThat(result)
                .isPresent()
                .hasValueSatisfying(jsonContent -> {
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathNumberValue("$.id").isEqualTo(1);
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathStringValue("$.description").isEqualTo("request");
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathStringValue("$.created").isEqualTo(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                    Assertions.assertThat(jsonContent)
                            .hasJsonPathValue("requestor");
                    Assertions.assertThat(jsonContent)
                            .hasJsonPathValue("items");
                });
    }

    ItemRequestDto getItemRequestDto(LocalDateTime localDateTime) {
        return new ItemRequestDto(1,
                "request",
                new UserDto(1, "user", "mail@mail.m"),
                localDateTime,
                new ArrayList<>());
    }
}