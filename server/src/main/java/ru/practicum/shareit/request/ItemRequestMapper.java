package ru.practicum.shareit.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface ItemRequestMapper {

    ItemRequestMapper MAP_REQUEST = Mappers.getMapper(ItemRequestMapper.class);

    ItemRequest toItemRequest(ItemRequestDto itemRequestDto);

    @Mapping(target = "requestor", source = "requestor")
    ItemRequestDtoResponse toItemRequestDtoResponse(ItemRequest itemRequest);

    Collection<ItemRequestDtoResponse> toCollectionItemRequestDto(Collection<ItemRequest> itemRequests);
}
