package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemDtoResponse addItem(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                   @Valid @RequestBody ItemDto itemDto) {
        log.info("Create {} by userId={}", itemDto.toString(), userId);
        return itemService.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDtoResponse updateItem(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                      @PathVariable @Min(0) long itemId,
                                      @RequestBody ItemDto itemDto) {
        log.info("Update {}", itemDto.toString());
        return itemService.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDtoResponse getItemById(@PathVariable @Min(0) long itemId) {
        log.info("GET Item id={}", itemId);
        return itemService.get(itemId);
    }

    @GetMapping
    public Collection<ItemDtoResponse> getAllItem(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId) {
        log.info("Items size {}", itemService.getSize());
        return itemService.getAllItemByUserId(userId);
    }

    @GetMapping("/search")
    public Collection<ItemDtoResponse> searchBySubstring(@RequestParam String text) {
        log.info("Search by text={}", text);
        return itemService.getBySubstring(text);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable @Min(0) long itemId) {
        log.info("Delete by id={}", itemId);
        itemService.delete(itemId);
    }

}
