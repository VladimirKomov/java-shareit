package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.ItemMapper.MAP_ITEM;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;


    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    public Item create(long userId, ItemDto data) {
        data.setOwner(userService.get(userId));
        return itemRepository.save(MAP_ITEM.toItem(data));
    }

    public Item get(long id) {
        return itemRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Item update(long userId, long id, ItemDto data) {
        validate(userId,
                MAP_ITEM.toItemDto(itemRepository.findById(id).orElseThrow(NotFoundException::new)));
        return itemRepository.save(updateValues(id, MAP_ITEM.toItem(data)));
    }

    protected Item updateValues(long id, Item data) {
        var target = itemRepository.findById(id).orElseThrow(NotFoundException::new);
        MAP_ITEM.update(data, target);

        return target;
    }

    public void delete(long id) {
        itemRepository.deleteById(id);
    }

    public int getSize() {
        //return itemRepository.count;
        return 0;
    }

    protected void validate(long userId, ItemDto data) {
        if (userId !=
                itemRepository.findById(data.getId()).orElseThrow(NotFoundException::new).getOwner().getId()) {
            throw new NotFoundException();
        }
    }

    public Collection<Item> getAllItemByUserId(long userId) {
        return itemRepository.findAll().stream()
                .filter(i -> i.getOwner().getId() == userId)
                .collect(Collectors.toList());
    }

    public Collection<Item> getBySubstring(String substr) {
        return substr.isBlank() ? List.of() : itemRepository.searchAvailableByNameAndDescription(substr).stream()
                .collect(Collectors.toList());
    }
}
