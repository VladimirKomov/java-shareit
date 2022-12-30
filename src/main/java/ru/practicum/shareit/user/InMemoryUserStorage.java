package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.model.AbstractStorage;

@Repository
public class InMemoryUserStorage extends AbstractStorage<User> {
}
