package ru.practicum.shareit;

import java.util.Collection;

public interface ObjectStorage<T> {

    T add(T object);

    void remove(int object);

    T modify(T object);

    Collection<T> getAll();

    T get(int id);
}
