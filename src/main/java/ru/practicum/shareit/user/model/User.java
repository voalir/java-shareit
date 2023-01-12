package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    Integer id;
    String name;
    String email;
}
