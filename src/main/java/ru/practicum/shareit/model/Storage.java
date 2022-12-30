package ru.practicum.shareit.model;

import java.util.List;
import java.util.Optional;

public interface Storage<T extends BaseModel> {
    T create(T data);

    void update(T data);

    Optional<T> get(long id);

    void delete(long id);

    List<T> getAll();

    int getSize();
}
