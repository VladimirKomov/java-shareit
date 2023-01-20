package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.ItemMapper.MAP_ITEM;

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
        return MAP_ITEM.toItemDtoResponse(itemService.create(userId, itemDto));
    }

    @PatchMapping("/{itemId}")
    public ItemDtoResponse updateItem(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                      @PathVariable @Min(0) long itemId,
                                      @RequestBody ItemDto itemDto) {
        log.info("Update {}", itemDto.toString());
        return MAP_ITEM.toItemDtoResponse(itemService.update(userId, itemId, itemDto));
    }

    @GetMapping("/{itemId}")
    public ItemDtoResponse getItemById(@PathVariable @Min(0) long itemId) {
        log.info("GET Item id={}", itemId);
        return MAP_ITEM.toItemDtoResponse(itemService.get(itemId));
    }

    @GetMapping
    public Collection<ItemDtoResponse> getAllItem(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId) {
        log.info("Items size {}", itemService.getSize());
        return itemService.getAllItemByUserId(userId).stream()
                .map(MAP_ITEM::toItemDtoResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public Collection<ItemDtoResponse> searchBySubstring(@RequestParam String text) {
        log.info("Search by text={}", text);
        return itemService.getBySubstring(text).stream()
                .map(MAP_ITEM::toItemDtoResponse)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable @Min(0) long itemId) {
        log.info("Delete by id={}", itemId);
        itemService.delete(itemId);
    }

}
