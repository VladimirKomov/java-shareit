package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;


import javax.validation.Valid;
import javax.validation.constraints.Min;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemClient itemClient;

    /**
     * Создаёт объект вещи пользователя userId
     */
    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                          @Valid @RequestBody ItemDto itemDto) {
        log.info("Create {} by userId={}", itemDto.toString(), userId);
        return itemClient.create(userId, itemDto);
    }

    /**
     * Создаёт комментарий вещи пользователя userId после бронирования
     */
    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                      @PathVariable @Min(0) long itemId,
                                      @RequestBody ItemDto itemDto) {
        log.info("Update {}", itemDto.toString());
        return itemClient.update(userId, itemId, itemDto);
    }

    /**
     * Возвращает вещь по itemId независимо от userId пользователя
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                           @PathVariable @Min(0) long itemId) {
        log.info("GET Item id={}", itemId);
        return itemClient.get(userId, itemId);
    }

    /**
     * Возвращает список всех вещей пользователя userId
     */
    @GetMapping
    public ResponseEntity<Object> getAllItem(
            @RequestHeader("X-Sharer-User-Id")
            @Validated @Min(0) long userId,
            @Validated @Min(0) @RequestParam(defaultValue = "0") int from,
            @Validated @Min(1) @RequestParam(defaultValue = "10") int size) {
        log.info("Items getAll");
        return itemClient.getAllItemByUserId(userId, from, size);
    }

    /**
     * Возвращает список по введенному тексту поиска text
     */
    @GetMapping("/search")
    public ResponseEntity<Object> searchBySubstring(
            @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @RequestParam String text,
            @Validated @Min(0) @RequestParam(defaultValue = "0") int from,
            @Validated @Min(1) @RequestParam(defaultValue = "10") int size) {
        log.info("Search by text={}", text);
        return itemClient.getBySubstring(text, userId, from, size);
    }

    /**
     * Удаляет объект вещи itemId пользователя userId
     */
    @DeleteMapping("/{itemId}")
    public void deleteItemById(
            @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @PathVariable @Min(0) long itemId) {
        log.info("Delete by id={}", itemId);
        itemClient.deleteItem(itemId, userId);
    }

    /**
     * Создаёт комментарий вещи пользователя userId после бронирования
     */
    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                         @PathVariable @Min(0) long itemId,
                                         @Valid @RequestBody CommentDto commentDto) {
        log.info("Create {} by userId={} for itemId={}", commentDto.toString(), userId, itemId);
        return itemClient.create(userId, itemId, commentDto);
    }

}
