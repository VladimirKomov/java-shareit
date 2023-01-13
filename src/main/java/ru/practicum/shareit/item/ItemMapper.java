package ru.practicum.shareit.item;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface ItemMapper {

    ItemMapper MAP = Mappers.getMapper(ItemMapper.class);

    ItemDto toItemDto(Item item);

    Item toItem(ItemDto itemDto);

    ItemDtoResponse toItemDtoResponse(Item item);

    ItemDtoResponse toItemDtoResponse(ItemDto itemDto);
}
