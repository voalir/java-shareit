package ru.practicum.shareit.user.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class User {
    int id;
    @NotBlank
    String name;
    @Email(message = "Указан некорректный email адрес")
    String email;
}
