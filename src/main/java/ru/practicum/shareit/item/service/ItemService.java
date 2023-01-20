package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;

import java.util.Collection;

public interface ItemService {
    Item create(long userId, ItemDto itemDto);

    Item update(long userId, long itemId, ItemDto itemDto);

    Item get(long id);

    Collection<Item> getAllItemByUserId(long id);

    Collection<Item> getBySubstring(String substring);

    void delete(long id);

    int getSize();
}
