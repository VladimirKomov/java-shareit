package ru.practicum.shareit.model;

import java.util.List;

public interface Service<T extends BaseModel> {

    T create(T data);
    T get(long id);
    T update(long id, T data);
    void delete(long id);
    void deleteAll();
    List<T> getAll();
    int getSize();
}
