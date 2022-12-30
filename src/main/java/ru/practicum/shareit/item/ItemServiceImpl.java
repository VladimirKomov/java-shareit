package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.model.Storage;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final Storage<Item> itemStorage;
    private final UserService userService;
    @Autowired
    public ItemServiceImpl(Storage<Item> itemStorage, UserService userService) {
        this.itemStorage = itemStorage;
        this.userService = userService;
    }

    private long generateId = 0L;

    public ItemDtoResponse create(long userId, ItemDto data) {
        data.setOwner(UserMapper.toUser(userService.get(userId)));
        data.setId(++generateId);
        return ItemMapper.toItemDtoResponse(itemStorage.create(ItemMapper.toItem(data)));
    }

    public ItemDtoResponse get(long id) {
        return ItemMapper.toItemDtoResponse(itemStorage.get(id).orElseThrow(NotFoundException::new));
    }

    public ItemDtoResponse update(long userId, long id, ItemDto data) {
        validate(userId,
                ItemMapper.toItemDto(itemStorage.get(id).orElseThrow(NotFoundException::new)));
        data = updateValues(id, data);
        itemStorage.update(ItemMapper.toItem(data));
        return ItemMapper.toItemDtoResponse(data);
    }

    protected ItemDto updateValues(long id, ItemDto data) {
        var recipient = ItemMapper.toItemDto(itemStorage.get(id).orElseThrow(NotFoundException::new));
        if (data.getName() != null) recipient.setName(data.getName());
        if (data.getDescription() != null) recipient.setDescription(data.getDescription());
        if (data.getAvailable() != null) recipient.setAvailable(data.getAvailable());

        return recipient;
    }

    public void delete(long id) {
        itemStorage.delete(id);
    }

    public int getSize() {
        return itemStorage.getSize();
    }

    protected  void validate(long userId, ItemDto data) {
               if (userId !=
                       itemStorage.get(data.getId()).orElseThrow(NotFoundException::new).getOwner().getId()) {
                throw new NotFoundException();
            }

    }

    public Collection<ItemDtoResponse> getAllItemByUserId(long userId) {
        return itemStorage.getAll().stream()
                .filter(i -> i.getOwner().getId() == userId)
                .map(ItemMapper::toItemDtoResponse)
                .collect(Collectors.toList());
    }

    public Collection<ItemDtoResponse> getBySubstring(String substr) {
        final String lowerCaseSubstr = substr.toLowerCase();
        return lowerCaseSubstr.isBlank() ? List.of() : itemStorage.getAll().stream()
                .filter(i -> i.getDescription().toLowerCase().contains(lowerCaseSubstr))
                .filter(Item::getAvailable)
                .map(ItemMapper::toItemDtoResponse)
                .collect(Collectors.toList());
    }
}
