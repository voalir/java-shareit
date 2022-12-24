package ru.practicum.shareit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

abstract public class InMemoryObjectStorage<T> implements ObjectStorage<T> {

    private final Map<Integer, T> objects = new HashMap<>();


    @Override
    abstract public T add(T object);

    @Override
    abstract public T modify(T object);

    @Override
    public Collection<T> getAll() {
        return objects.values();
    }

    @Override
    public void remove(int id) {
        objects.remove(id);
    }

    @Override
    public T get(int id) {
        return objects.getOrDefault(id, null);
    }

    protected void putObject(Integer id, T object) {
        objects.put(id, object);
    }

    protected boolean containsObjectKey(Integer id) {
        return objects.containsKey(id);
    }

}
