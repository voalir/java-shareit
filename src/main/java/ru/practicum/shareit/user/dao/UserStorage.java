package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

@Deprecated
public interface UserStorage {

    User add(User user);

    void remove(Integer id);

    User modify(User object);

    Collection<User> getAll();

    User get(Integer id);

    boolean hasUserWithEmail(String email);

}
