package ru.practicum.shareit.item.srorage;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryItemStorage implements ItemStorage{

    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item addItem(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item updateItem(long itemId, Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public void deleteItem(long itemId) {
        items.remove(itemId);
    }

    @Override
    public Optional<Item> getItemById(long itemId) {
        return Optional.of(items.get(itemId));
    }

    @Override
    public Collection<Item> getAllItem() {
        return items.values();
    }

    @Override
    public Collection<Item> getAllItemByUserId(long userId) {
        return items.values().stream()
                .filter(i -> i.getOwner().getId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Item> getBySubstring(String substr) {
        return items.values().stream()
                .filter(i -> i.getDescription().contentEquals(substr))
                .collect(Collectors.toList());
    }

    @Override
    public int getSize() {
        return items.size();
    }
}
