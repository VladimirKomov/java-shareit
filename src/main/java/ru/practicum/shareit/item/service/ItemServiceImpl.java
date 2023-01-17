package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.ItemMapper.MAP_ITEM;
import static ru.practicum.shareit.user.UserMapper.MAP_USER;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;


    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    public ItemDtoResponse create(long userId, ItemDto data) {
        data.setOwner(MAP_USER.toUser(userService.get(userId)));
        return MAP_ITEM.toItemDtoResponse(
                itemRepository.save(MAP_ITEM.toItem(data)));
    }

    public ItemDtoResponse get(long id) {
        return MAP_ITEM.toItemDtoResponse(
                itemRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public ItemDtoResponse update(long userId, long id, ItemDto data) {
        validate(userId,
                MAP_ITEM.toItemDto(itemRepository.findById(id).orElseThrow(NotFoundException::new)));
        data = updateValues(id, data);
        itemRepository.save(MAP_ITEM.toItem(data));
        return MAP_ITEM.toItemDtoResponse(data);
    }

    protected ItemDto updateValues(long id, ItemDto data) {
        var target = MAP_ITEM.toItemDto(itemRepository.findById(id).orElseThrow(NotFoundException::new));
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

    public Collection<ItemDtoResponse> getAllItemByUserId(long userId) {
        return itemRepository.findAll().stream()
                .filter(i -> i.getOwner().getId() == userId)
                .map(MAP_ITEM::toItemDtoResponse)
                .collect(Collectors.toList());
    }

    public Collection<ItemDtoResponse> getBySubstring(String substr) {
//        final String lowerCaseSubstr = substr.toLowerCase();
//        return lowerCaseSubstr.isBlank() ? List.of() : itemRepository.findAll().stream()
//                .filter(i -> i.getDescription().toLowerCase().contains(lowerCaseSubstr))
//                .filter(Item::getAvailable)
//                .map(ItemMapper.MAP::toItemDtoResponse)
//                .collect(Collectors.toList());
        return substr.isBlank() ? List.of() : itemRepository.searchByNameAndDescription(substr).stream()
                .map(MAP_ITEM::toItemDtoResponse)
                .collect(Collectors.toList());
    }
}
