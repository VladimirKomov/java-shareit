package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.ItemMapper.MAP_ITEM;
import static ru.practicum.shareit.request.ItemRequestMapper.MAP_REQUEST;

@RequiredArgsConstructor
@Service
public class ItemRequestServiceImp implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDtoResponse create(long userId, ItemRequestDto itemRequestDto) {
        User user = userService.getEntity(userId);
        ItemRequest newItemRequest = MAP_REQUEST.toItemRequest(itemRequestDto);
        newItemRequest.setRequestor(user);
        newItemRequest.setCreated(LocalDateTime.now());
        return MAP_REQUEST.toItemRequestDtoResponse(itemRequestRepository.save(newItemRequest));
    }

    @Override
    public Collection<ItemRequestDtoResponse> getRequestsByRequestorId(long userId) {
        userService.getEntity(userId);
//        return MAP_REQUEST.toCollectionItemRequestDto(
//                itemRequestRepository.findAllByRequestorIdOrderByCreatedDesc(userId));
        Collection<ItemRequestDtoResponse> itemRequestsDto = MAP_REQUEST.toCollectionItemRequestDto(
                itemRequestRepository.findAllByRequestorIdOrderByCreatedDesc(userId));
        return setItems(itemRequestsDto);
    }

    private Collection<ItemRequestDtoResponse> setItems(Collection<ItemRequestDtoResponse> itemRequestsDto) {
        //получить все items за одно обращение
        List<Long> requestId = itemRequestsDto.stream()
                .map(ItemRequestDtoResponse::getId)
                .collect(Collectors.toList());
        Collection<Item> items = itemService.getByRequests(requestId);

        //установить каждому request найденный список items
        itemRequestsDto.stream().forEach(
                itemRequestDtoResponse -> itemRequestDtoResponse.setItems(
                        MAP_ITEM.toCollectionItemDtoResponse(
                                items.stream()
                                        .filter(item -> item.getRequest().getId() == itemRequestDtoResponse.getId())
                                        .collect(Collectors.toList())
                        )
                )
        );


        return itemRequestsDto;


    }

    private void setItemsForRequest(ItemRequestDtoResponse requestDtoResponse, Collection<Item> items) {
        requestDtoResponse.setItems(
                MAP_ITEM.toCollectionItemDtoResponse(
                        items.stream()
                                .filter(item -> item.getRequest().getId() == requestDtoResponse.getId())
                                .collect(Collectors.toList())
                )
        );
    }

    @Override
    public Collection<ItemRequestDtoResponse> getAll(long userId, int from, int size) {
        return MAP_REQUEST.toCollectionItemRequestDto(
                itemRequestRepository.findAllByRequestorIdOrderByCreatedDesc(userId, PageRequest.of(from, size)));
    }
}
