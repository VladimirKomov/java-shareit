package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable(),
                item.getOwner(), item.getRequest());
    }

    public static Item toItem(ItemDto itemDto) {
        return new Item(itemDto.getId(), itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable(),
                itemDto.getOwner(), itemDto.getRequest());
    }

    public static ItemDtoResponse toItemDtoResponse(Item item) {
        return new ItemDtoResponse(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
    }

    public static ItemDtoResponse toItemDtoResponse(ItemDto itemDto) {
        return new ItemDtoResponse(itemDto.getId(), itemDto.getName(),
                itemDto.getDescription(), itemDto.getAvailable());
    }

}
