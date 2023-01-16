package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserService userService, UserMapper userMapper) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    public ItemDtoResponse create(long userId, ItemDto data) {
        data.setOwner(UserMapper.MAP.toUser(userService.get(userId)));
        return ItemMapper.MAP.toItemDtoResponse(itemRepository.save(ItemMapper.MAP.toItem(data)));
    }

    public ItemDtoResponse get(long id) {
        return ItemMapper.MAP.toItemDtoResponse(itemRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public ItemDtoResponse update(long userId, long id, ItemDto data) {
        validate(userId,
                ItemMapper.MAP.toItemDto(itemRepository.findById(id).orElseThrow(NotFoundException::new)));
        data = updateValues(id, data);
        itemRepository.save(ItemMapper.MAP.toItem(data));
        return ItemMapper.MAP.toItemDtoResponse(data);
    }

    protected ItemDto updateValues(long id, ItemDto data) {
        var target = ItemMapper.MAP.toItemDto(itemRepository.findById(id).orElseThrow(NotFoundException::new));
        ItemMapper.MAP.update(data, target);

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

    public Collection<ItemDtoResponse> getAllItemByUserId(long userId) {
        return itemRepository.findAll().stream()
                .filter(i -> i.getOwner().getId() == userId)
                .map(ItemMapper.MAP::toItemDtoResponse)
                .collect(Collectors.toList());
    }

    public Collection<ItemDtoResponse> getBySubstring(String substr) {
        final String lowerCaseSubstr = substr.toLowerCase();
        return lowerCaseSubstr.isBlank() ? List.of() : itemRepository.findAll().stream()
                .filter(i -> i.getDescription().toLowerCase().contains(lowerCaseSubstr))
                .filter(Item::getAvailable)
                .map(ItemMapper.MAP::toItemDtoResponse)
                .collect(Collectors.toList());
    }
}
