package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.Collection;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    /**
     * Создаёт объект запрос
     */
    @PostMapping
    public ItemRequestDtoResponse addItemRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                                 @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Create {}", itemRequestDto.toString());
        return itemRequestService.create(userId, itemRequestDto);
    }

    /**
     * Возвращает список всех запросов userId
     */
    @GetMapping
    public Collection<ItemRequestDtoResponse> getAllItemRequest(
            @Validated @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("ItemRequests getAll by userId={}", userId);
        return itemRequestService.getRequestsByRequestorId(userId);
    }

    /**
     * Возвращает запрос по userId и requestId
     */
    @GetMapping("/{requestId}")
    public ItemRequestDtoResponse getItemRequestById(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @PathVariable long requestId) {
        log.info("ItemRequest get by userId={} and requestId={}", userId, requestId);
        return itemRequestService.getRequestsByRequestId(userId, requestId);
    }

    /**
     * Возвращает список запросов, созданных другими пользователями
     */
    @GetMapping("/all")
    public Collection<ItemRequestDtoResponse> getAllItemRequests(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        return itemRequestService.getAll(userId, from, size);
    }
}
