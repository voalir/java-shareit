package ru.practicum.shareit.item.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.ShortBookingDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@JsonTest
class ItemDtoTest {
    @Autowired
    private JacksonTester<ItemDto> json;

    @Test
    void itemDtoTest() throws IOException {
        ItemDto itemDto = getItemDto();

        Optional<JsonContent<ItemDto>> result = Optional.of(json.write(itemDto));

        Assertions.assertThat(result)
                .isPresent()
                .hasValueSatisfying(jsonContent -> {
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathNumberValue("$.id").isEqualTo(1);
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathStringValue("$.name").isEqualTo("name item");
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathStringValue("$.description").isEqualTo("description");
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathBooleanValue("$.available").isEqualTo(true);
                    Assertions.assertThat(jsonContent)
                            .hasJsonPathValue("lastBooking");
                    Assertions.assertThat(jsonContent)
                            .hasJsonPathValue("nextBooking");
                    Assertions.assertThat(jsonContent)
                            .hasJsonPathArrayValue("comments");
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathNumberValue("$.requestId").isEqualTo(1);
                });
    }

    ItemDto getItemDto() {
        return new ItemDto(
                1,
                "name item",
                "description",
                true,
                1,
                new ShortBookingDto(1, 1),
                new ShortBookingDto(3, 3),
                new ArrayList<>()
        );
    }
}