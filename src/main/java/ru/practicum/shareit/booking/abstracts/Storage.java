package ru.practicum.shareit.booking.abstracts;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface Storage<T extends BaseModel> {
    void create(T data);

    void update(T data);

    T get(long id);

    void delete(long id);

    void deleteAll();

    List<T> getAll();

    int getSize();
}
