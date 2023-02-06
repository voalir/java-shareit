package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private final Integer id;
    private final String name;
    @Email(message = "Указан некорректный email адрес")
    private final String email;


}
