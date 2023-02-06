package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class CommentDto {

    private final Integer id;
    @NotBlank
    private final String text;
    private final String authorName;

}
