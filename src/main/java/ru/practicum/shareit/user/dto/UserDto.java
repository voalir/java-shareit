package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class UserDto {

    Integer id;
    String name;
    @Email(message = "Указан некорректный email адрес")
    String email;


}
