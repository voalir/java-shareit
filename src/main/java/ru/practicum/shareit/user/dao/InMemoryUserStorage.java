package ru.practicum.shareit.user.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.InMemoryObjectStorage;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;

@Component
public class InMemoryUserStorage extends InMemoryObjectStorage<User> implements UserStorage {

    @Override
    public User add(User user) {
        super.putObject(user.getId(), user);
        return get(user.getId());
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return super.getAll().stream().anyMatch(s -> s.getEmail().equals(email));
    }

    @Override
    public User get(int id) {
        if (super.containsObjectKey(id)) {
            return super.get(id);
        } else {
            throw new UserNotFoundException(String.format("user with id=%s not found", id));
        }
    }

    @Override
    public User modify(User user) {
        super.putObject(user.getId(), user);
        return super.get(user.getId());
    }

}
