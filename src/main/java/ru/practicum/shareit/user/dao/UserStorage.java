package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserStorage {

    User add(User user);

    void remove(int id);

    User modify(User object);

    Collection<User> getAll();

    User get(int id);

    boolean hasUserWithEmail(String email);

}
