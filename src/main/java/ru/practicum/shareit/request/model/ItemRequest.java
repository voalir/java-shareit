package ru.practicum.shareit.request.model;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.util.Date;

/**
 * TODO Sprint add-item-requests.
 */
@Data
public class ItemRequest {
    int id;
    String description;
    User requestor;
    Date created;
}
