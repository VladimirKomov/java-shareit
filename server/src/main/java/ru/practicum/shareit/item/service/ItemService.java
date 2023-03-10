package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.*;

import java.util.Collection;
import java.util.List;

public interface ItemService {
    ItemDtoResponse create(long userId, ItemDto itemDto);

    ItemDtoResponse update(long userId, long itemId, ItemDto itemDto);

    ItemDtoResponseLong get(long userId, long itemId);

    Item getEntity(long id);

    Collection<ItemDtoResponseLong> getAllItemByUserId(long id, int from, int size);

    Collection<ItemDtoResponse> getBySubstring(String substring, int from, int size);

    void delete(long id);

    CommentDtoResponse create(long userId, long itemId, CommentDto commentDto);

    Collection<Item> getByRequests(List<Long> listRequestId);
}
