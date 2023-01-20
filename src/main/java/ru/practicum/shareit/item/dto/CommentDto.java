package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class CommentDto {

    private Integer id;
    @NotBlank
    private String text;
    private String authorName;

}
