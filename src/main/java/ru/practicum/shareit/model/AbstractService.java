package ru.practicum.shareit.model;

import ru.practicum.shareit.exception.NotFoundException;

import java.util.List;

public abstract class AbstractService<T extends BaseModel> implements Service<T> {

    private long generateId = 0L;

    protected Storage<T> storage;

    @Override
    public T create(T data) {
        validate(data);
        data.setId(++generateId);
        storage.create(data);
        return data;
    }

    protected abstract void validate(T data);

    @Override
    public T get(long id) {
        return storage.get(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public T update(long id, T data) {
        updateValues(id, data);
        storage.update(data);
        return data;
    }

    protected abstract void updateValues(long id, T data);

    @Override
    public void delete(long id) {
        storage.delete(id);
    }

    @Override
    public void deleteAll() {
        storage.deleteAll();
    }

    @Override
    public List<T> getAll() {
        return storage.getAll();
    }

    @Override
    public int getSize() {
        return storage.getSize();
    }


}
