package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.dto.ShortBookingDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class ItemDto {

    Integer id;
    @NotBlank
    String name;
    @NotBlank
    String description;
    @NotNull
    Boolean available;
    Integer requestId;
    ShortBookingDto nextBooking;
    ShortBookingDto lastBooking;
    List<CommentDto> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        if (!name.equals(itemDto.name)) return false;
        if (!description.equals(itemDto.description)) return false;
        if (!available.equals(itemDto.available)) return false;
        if (!Objects.equals(requestId, itemDto.requestId)) return false;
        if (!Objects.equals(nextBooking, itemDto.nextBooking)) return false;
        if (!Objects.equals(lastBooking, itemDto.lastBooking)) return false;
        return Objects.equals(comments, itemDto.comments);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + available.hashCode();
        result = 31 * result + (requestId != null ? requestId.hashCode() : 0);
        result = 31 * result + (nextBooking != null ? nextBooking.hashCode() : 0);
        result = 31 * result + (lastBooking != null ? lastBooking.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }
}
