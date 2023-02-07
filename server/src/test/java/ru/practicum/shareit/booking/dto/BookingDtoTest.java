package ru.practicum.shareit.booking.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@JsonTest
class BookingDtoTest {

    @Autowired
    private JacksonTester<BookingDto> json;
    @Autowired
    private JacksonTester<ShortBookingDto> jsonShort;

    @Test
    void bookingDtoTest() throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();
        BookingDto bookingDto = getBookingDto(localDateTime);
        Optional<JsonContent<BookingDto>> result = Optional.of(json.write(bookingDto));

        Assertions.assertThat(result)
                .isPresent()
                .hasValueSatisfying(jsonContent -> {
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathNumberValue("$.id").isEqualTo(1);
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathStringValue("$.start").isEqualTo(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathStringValue("$.end").isEqualTo(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathNumberValue("$.itemId").isEqualTo(1);
                    Assertions.assertThat(jsonContent)
                            .hasJsonPathValue("booker");
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathStringValue("$.status").isEqualTo("WAITING");
                });

    }

    @Test
    void shortBookingTest() throws IOException {
        ShortBookingDto shortBookingDto = getShortBookingDto();
        Optional<JsonContent<ShortBookingDto>> result = Optional.of(jsonShort.write(shortBookingDto));

        Assertions.assertThat(result)
                .isPresent()
                .hasValueSatisfying(jsonContent -> {
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathNumberValue("$.id").isEqualTo(1);
                    Assertions.assertThat(jsonContent)
                            .extractingJsonPathNumberValue("$.bookerId").isEqualTo(1);
                });
    }

    BookingDto getBookingDto(LocalDateTime localDateTime) {
        return new BookingDto(1,
                localDateTime,
                localDateTime,
                1,
                null,
                new UserDto(1, "name", "mail"),
                BookingStatus.WAITING);
    }

    ShortBookingDto getShortBookingDto() {
        return new ShortBookingDto(1, 1);
    }
}