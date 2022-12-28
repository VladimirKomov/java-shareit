package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {

    Item addItem(long userId, Item item);

    Item updateItem(long userId, long itemId, Item item);

    void deleteItem(long itemId);

    Item getItemById(long itemId);

    Collection<Item> getAllItem();

    Collection<Item> getAllItemByUserId(long userId);

    Collection<Item> getBySubstring(String substr);

    int getSize();
}
