package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.ObjectStorage;
import ru.practicum.shareit.user.model.User;

public interface UserStorage extends ObjectStorage<User> {

    boolean hasUserWithEmail(String email);

}
