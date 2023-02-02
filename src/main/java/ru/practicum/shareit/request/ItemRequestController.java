package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.constraints.Min;
import java.util.Collection;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    /**
     * Создаёт объект запрос
     */
    @PostMapping
    public ItemRequestDtoResponse addItemRequest(@Validated @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                                 @RequestBody @NotNull ItemRequestDto itemRequestDto) {
        log.info("Create {}", itemRequestDto.toString());
        return itemRequestService.create(userId, itemRequestDto);
    }

    /**
     * Возвращает список всех запросов userId
     */
    @GetMapping
    public Collection<ItemRequestDtoResponse> getAllItemRequest(
            @Validated @RequestHeader("X-Sharer-User-Id") @Min(0) long userId) {
        log.info("ItemRequests getAll by userId={}", userId);
        return itemRequestService.getRequestsByRequestorId(userId);
    }

    /**
     * Возвращает запрос по userId и requestId
     */
    @GetMapping("/{requestId}")
    public ItemRequestDtoResponse getItemRequestById(
            @Validated @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @Min(0) @PathVariable long requestId) {
        log.info("ItemRequest get by userId={} and requestId={}", userId, requestId);
        return itemRequestService.getRequestsByRequestId(userId, requestId);
    }

    /**
     * Возвращает список запросов, созданных другими пользователями
     */
    @GetMapping("/all")
    public Collection<ItemRequestDtoResponse> getAllItemRequests(
            @Validated @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @Validated @Min(0) @RequestParam(defaultValue = "0") int from,
            @Validated @Min(1) @RequestParam(defaultValue = "10") int size) {
        return itemRequestService.getAll(userId, from, size);
    }
}
