package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.CreateValidationGroup;
import ru.practicum.shareit.user.UpdateValidationGroup;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private final Integer id;
    private final String name;
    @NotBlank(groups = CreateValidationGroup.class)
    @Email(groups = {CreateValidationGroup.class, UpdateValidationGroup.class}, message = "Указан некорректный email адрес")
    private final String email;


}
