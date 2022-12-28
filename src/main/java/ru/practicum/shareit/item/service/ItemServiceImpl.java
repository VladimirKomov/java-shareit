package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.srorage.ItemStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemStorage itemStorage;

    @Override
    public Item addItem(long userId, Item item) {
        return null;
    }

    @Override
    public Item updateItem(long userId, long itemId, Item item) {
        return null;
    }

    @Override
    public void deleteItem(long itemId) {

    }

    @Override
    public Item getItemById(long itemId) {
        return null;
    }

    @Override
    public Collection<Item> getAllItem() {
        return null;
    }

    @Override
    public Collection<Item> getAllItemByUserId(long userId) {
        return null;
    }

    @Override
    public Collection<Item> getBySubstring(String substr) {
        return null;
    }

    @Override
    public int getSize() {
        return itemStorage.getSize();
    }
}
