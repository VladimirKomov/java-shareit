package ru.practicum.shareit.booking.model;


import java.util.*;

public abstract class AbstractStorage<T extends BaseModel> implements Storage<T>{

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
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int getSize() {
        return storage.size();
    }

    @Override
    public Optional<T> get(T data) {
        return Optional.of(storage.values().stream()
                .filter(d -> d.canEqual(data))
                .findFirst().get()
        );
    }
}
