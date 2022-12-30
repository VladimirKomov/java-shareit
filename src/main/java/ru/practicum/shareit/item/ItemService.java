package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;

import java.util.Collection;

public interface ItemService {
    ItemDtoResponse create(long userId, ItemDto itemDto);

    ItemDtoResponse update(long userId, long itemId, ItemDto itemDto);

    ItemDtoResponse get(long id);

    Collection<ItemDtoResponse> getAllItemByUserId(long id);

    Collection<ItemDtoResponse> getBySubstring(String substring);

    void delete(long id);

    int getSize();
}
