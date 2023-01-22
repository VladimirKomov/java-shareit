package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.*;

import java.util.Collection;

public interface ItemService {
    ItemDtoResponse create(long userId, ItemDto itemDto);

    ItemDtoResponse update(long userId, long itemId, ItemDto itemDto);

    ItemDtoResponseLong get(long userId, long itemId);

    Item getEntity (long id);

    Collection<ItemDtoResponseLong> getAllItemByUserId(long id);

    Collection<ItemDtoResponse> getBySubstring(String substring);

    void delete(long id);

    int getSize();

    CommentDtoResponse create(long userId, long itemId, CommentDto commentDto);
}
