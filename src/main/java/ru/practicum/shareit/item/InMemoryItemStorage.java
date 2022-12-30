package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.model.AbstractStorage;

@Repository
public class InMemoryItemStorage extends AbstractStorage<Item> {
}
