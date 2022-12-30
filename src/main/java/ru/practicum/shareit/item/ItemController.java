package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @Valid @RequestBody ItemDto itemDto) {
        log.info("Create {} by userId={}",  itemDto.toString(), userId);
        return itemService.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @PathVariable long itemId,
                           @RequestBody ItemDto itemDto) {
        log.info("Update {}",  itemDto.toString());
        return itemService.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable long itemId) {
        log.info("GET Item id={}", itemId);
        return itemService.get(itemId);
    }

//    @GetMapping
//    public Collection<ItemDto> getAllItems() {
//        log.info("Items size {}", itemService.getSize());
//        return itemService.getAll();
//    }

    @GetMapping
    public Collection<ItemDto> getAllItem(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Items size {}", itemService.getSize());
        return itemService.getAllItemByUserId(userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchBySubstring(@RequestParam String text) {
        log.info("Search by text={}", text);
        return itemService.getBySubstring(text);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable long itemId) {
        log.info("Delete by id={}", itemId);
        itemService.delete(itemId);
    }


}
