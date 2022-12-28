package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

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
    public Item addItem(@RequestHeader("X-Sharer-User-Id") long userId,
                        @RequestBody Item item) {
        log.info("Create {}",  item.toString());
        return itemService.addItem(userId, item);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @PathVariable long itemId,
                           @RequestBody Item item) {
        log.info("Update {}",  item.toString());
        return itemService.updateItem(userId, itemId, item);
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable long itemId) {
        log.info("GET Item id={}", itemId);
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public Collection<Item> getAllItem(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Items size {}", itemService.getSize());
        return itemService.getAllItemByUserId(userId);
    }

    @GetMapping("/search")
    public Collection<Item> searchBySubstring(@RequestParam String text) {
        log.info("Search by text={}", text);
        return itemService.getBySubstring(text);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable long itemId) {
        log.info("Delete by id={}", itemId);
        itemService.deleteItem(itemId);
    }


}
