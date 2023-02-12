package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;

import java.util.Collection;

public interface ItemRequestService {

    ItemRequestDtoResponse create(long userId, ItemRequestDto itemRequestDto);

    Collection<ItemRequestDtoResponse> getRequestsByRequestorId(long userId);

    Collection<ItemRequestDtoResponse> getAll(long userId, int from, int size);

   ItemRequestDtoResponse getRequestsByRequestId(long userId, long requestId);
}
