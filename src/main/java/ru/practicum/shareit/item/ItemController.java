package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * Создаёт объект вещи пользователя userId
     */
    @PostMapping
    public ItemDtoResponse addItem(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                   @Valid @RequestBody ItemDto itemDto) {
        log.info("Create {} by userId={}", itemDto.toString(), userId);
        return itemService.create(userId, itemDto);
    }

    /**
     * Создаёт комментарий вещи пользователя userId после бронирования
     */
    @PatchMapping("/{itemId}")
    public ItemDtoResponse updateItem(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                      @PathVariable @Min(0) long itemId,
                                      @RequestBody ItemDto itemDto) {
        log.info("Update {}", itemDto.toString());
        return itemService.update(userId, itemId, itemDto);
    }

    /**
     * Возвращает вещь по itemId независимо от userId пользователя
     */
    @GetMapping("/{itemId}")
    public ItemDtoResponseLong getItemById(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                           @PathVariable @Min(0) long itemId) {
        log.info("GET Item id={}", itemId);
        return itemService.get(userId, itemId);
    }

    /**
     * Возвращает список всех вещей пользователя userId
     */
    @GetMapping
    public Collection<ItemDtoResponseLong> getAllItem(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId) {
        log.info("Items getAll");
        return itemService.getAllItemByUserId(userId);
    }

    /**
     * Возвращает список по введенному тексту поиска text
     */
    @GetMapping("/search")
    public Collection<ItemDtoResponse> searchBySubstring(@RequestParam String text) {
        log.info("Search by text={}", text);
        return itemService.getBySubstring(text);
    }

    /**
     * Удаляет объект вещи itemId пользователя userId
     */
    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable @Min(0) long itemId) {
        log.info("Delete by id={}", itemId);
        itemService.delete(itemId);
    }

    /**
     * Создаёт комментарий вещи пользователя userId после бронирования
     */
    @PostMapping("/{itemId}/comment")
    public CommentDtoResponse addComment(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                         @PathVariable @Min(0) long itemId,
                                         @Valid @RequestBody CommentDto commentDto) {
        log.info("Create {} by userId={} for itemId={}", commentDto.toString(), userId, itemId);
        return itemService.create(userId, itemId, commentDto);
    }

}
