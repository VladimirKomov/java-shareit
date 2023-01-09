package ru.practicum.shareit.model;


import java.util.*;

public abstract class AbstractStorage<T extends BaseModel> implements Storage<T> {

    private final Map<Long, T> storage = new HashMap<>();

    @Override
    public T create(T data) {
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public void update(T data) {
        storage.put(data.getId(), data);
    }

    @Override
    public Optional<T> get(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void delete(long id) {
        storage.remove(id);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int getSize() {
        return storage.size();
    }

}
