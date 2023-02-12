package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;


@Slf4j
@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class ItemRequestController {

    private final ItemRequestClient itemRequestClient;


    /**
     * Создаёт объект запрос
     */
    @PostMapping
    public ResponseEntity<Object> addItemRequest(
            @Validated @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @Valid @RequestBody ItemRequestDto dto) {
        log.info("Create {}", dto.toString());
        return itemRequestClient.create(userId, dto);
    }

    /**
     * Возвращает список всех запросов userId
     */
    @GetMapping
    public ResponseEntity<Object> getAllItemRequest(
            @Validated @RequestHeader("X-Sharer-User-Id") @Min(0) long userId) {
        log.info("ItemRequests getAll by userId={}", userId);
        return itemRequestClient.getItemRequests(userId);
    }

    /**
     * Возвращает список запросов, созданных другими пользователями
     */
    @GetMapping("all")
    public ResponseEntity<Object> getAllItemRequests(
            @Validated @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @Validated @Min(0) @RequestParam(defaultValue = "0") int from,
            @Validated @Min(1) @RequestParam(defaultValue = "10") int size) {

        return itemRequestClient.getItemRequestsPage(userId, from, size);

    }

    /**
     * Возвращает запрос по userId и requestId
     */
    @GetMapping("{requestId}")
    public ResponseEntity<Object> findItemRequestById(
            @Validated @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @Min(0) @PathVariable long requestId) {
        log.info("ItemRequest get by userId={} and requestId={}", userId, requestId);
        return itemRequestClient.getItemRequest(userId, requestId);
    }
}

