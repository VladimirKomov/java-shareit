package ru.practicum.shareit.item;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;

@Mapper(componentModel = "spring", uses = ItemMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ItemMapper {

    ItemMapper MAP = Mappers.getMapper(ItemMapper.class);

    ItemDto toItemDto(Item item);

    Item toItem(ItemDto itemDto);

    ItemDtoResponse toItemDtoResponse(Item item);

    ItemDtoResponse toItemDtoResponse(ItemDto itemDto);

    @Mapping(target = "id", ignore = true)
    void update(ItemDto donor, @MappingTarget ItemDto target);
}
