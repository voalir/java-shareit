package ru.practicum.shareit.item.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.Optional;

@JsonTest
class CommentDtoTest {

    @Autowired
    private JacksonTester<CommentDto> json;

    @Test
    void toCommentDtoTest() throws IOException {
        String jsonString = "{\"text\": \"text comment\"}";

        Optional<CommentDto> dto = Optional.of(json.parseObject(jsonString));

        Assertions.assertThat(dto)
                .isPresent()
                .hasValueSatisfying(i -> Assertions.assertThat(i)
                        .hasFieldOrPropertyWithValue("text", "text comment"));
    }

    @Test
    void fromCommentDtoTest() throws IOException {
        CommentDto commentDto = new CommentDto(1, "text", "user");

        Optional<JsonContent<CommentDto>> result = Optional.of(json.write(commentDto));

        Assertions.assertThat(result)
                .isPresent()
                .hasValueSatisfying(i -> {
                    Assertions.assertThat(i)
                            .extractingJsonPathNumberValue("$.id").isEqualTo(1);
                    Assertions.assertThat(i)
                            .extractingJsonPathStringValue("$.text").isEqualTo("text");
                    Assertions.assertThat(i)
                            .extractingJsonPathStringValue("$.authorName").isEqualTo("user");
                });
    }

}