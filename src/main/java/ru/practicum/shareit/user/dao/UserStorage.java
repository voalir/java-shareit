package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

public interface UserStorage {
    void addUser(User toUser);

    boolean hasUserWithEmail(String email);

    User getUser(Integer id);

    void updateUser(User user);

    void deleteUser(Integer userId);
}
