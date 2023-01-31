package ru.practicum.shareit.item;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.dto.ItemDtoResponseLong;

import java.util.Collection;

@Mapper(componentModel = "spring", uses = ItemMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ItemMapper {

    ItemMapper MAP_ITEM = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "requestId", source = "request.id")
    ItemDto toItemDto(Item item);

     Item toItem(ItemDto itemDto);

    @Mapping(target = "requestId", source = "request.id")
    ItemDtoResponse toItemDtoResponse(Item item);

    ItemDtoResponseLong toItemDtoRespLong(Item item);

    Collection<ItemDtoResponse> toCollectionItemDtoResponse(Collection<Item> items);

    Collection<ItemDtoResponseLong> toCollectionItemDtoResponseLong(Collection<Item> items);

    @Mapping(target = "id", ignore = true)
    void update(Item donor, @MappingTarget Item target);
}
