package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Storage;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    private final Storage<Item> itemStorage;
    private final UserService userService;
    @Autowired
    public ItemService(Storage<Item> itemStorage, UserService userService) {
        this.itemStorage = itemStorage;
        this.userService = userService;
    }

    private long generateId = 0L;

    public ItemDto create(long userId, ItemDto data) {
        userService.get(userId);
        validate(data);
        data.setId(++generateId);
        itemStorage.create(ItemMapper.toItem(data));
        return data;
    }

    protected  void validate(ItemDto data) {

    }

    public ItemDto get(long id) {
        return ItemMapper.toItemDto(itemStorage.get(id).orElseThrow(NotFoundException::new));
    }


    public ItemDto update(long id, ItemDto data) {
        //validate(data);
        //data.setId(storage.get(id).orElseThrow(NotFoundException::new).getId());
        updateValues(id, data);
        itemStorage.update(ItemMapper.toItem(data));
        return data;
    }

    protected void updateValues(long id, ItemDto data) {

    };

    public void delete(long id) {
        itemStorage.delete(id);
    }

    public void deleteAll() {
        itemStorage.deleteAll();
    }

    public List<ItemDto> getAll() {
        List<ItemDto> listItemDto = new ArrayList<>();
        for (Item value: itemStorage.getAll()) {
            listItemDto.add(ItemMapper.toItemDto(value));
        }

        return listItemDto;
    }

    public int getSize() {
        return itemStorage.getSize();
    }

}
