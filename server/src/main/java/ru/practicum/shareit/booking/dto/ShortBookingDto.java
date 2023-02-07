package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShortBookingDto {
    private final Integer id;
    private final Integer bookerId;
}
