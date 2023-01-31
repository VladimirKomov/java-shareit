package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private BookingService bookingService;

    ItemDto itemDto;

    @Test
    public void addItem() throws Exception {
        String json = "{\n" +
                "    \"name\": \"Дрель\",\n" +
                "    \"description\": \"Простая дрель\",\n" +
                "    \"available\": true\n" +
                "}";
        itemDto = ItemDto.builder()
                .id(1L)
                .description("Простая дрель")
                .name("Дрель")
                .request(ItemRequest.builder()
                        .id(1L).description("description")
                        .build())
                .owner(UserDto.builder().id(1).name("User").build())
                .available(true)
                .build();
        when(itemService.addItem(anyLong(), any()))
                .thenReturn(item);

        this.mockMvc
                .perform(post("/items")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void updateItem() throws Exception {
        String json = "{\n" +
                "    \"name\": \"Дрель+\",\n" +
                "    \"description\": \"Аккумуляторная дрель\",\n" +
                "    \"available\": false\n" +
                "}";
        item = Item.builder()
                .id(1L)
                .description("Аккумуляторная дрель")
                .name("Дрель+")
                .request(ItemRequest.builder()
                        .id(1L).description("description")
                        .build())
                .owner(User.builder().id(1).name("User").build())
                .available(false)
                .build();
        when(itemService.updateItem(anyLong(), anyLong(), any()))
                .thenReturn(item);

        this.mockMvc
                .perform(patch("/items/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Дрель+")));
    }

    @Test
    public void getItemById() throws Exception {
        item = Item.builder()
                .id(1L)
                .description("Аккумуляторная дрель")
                .name("Дрель+")
                .request(ItemRequest.builder()
                        .id(1L).description("description")
                        .build())
                .owner(User.builder().id(1).name("User").build())
                .available(false)
                .build();
        when(itemService.getItemById(anyLong()))
                .thenReturn(item);

        this.mockMvc
                .perform(get("/items/1")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Дрель+")));
    }

    @Test
    public void getAllItem() throws Exception {
        Collection<Item> items = new ArrayList<>();
        item = Item.builder()
                .id(1L)
                .description("Аккумуляторная дрель")
                .name("Дрель+")
                .request(ItemRequest.builder()
                        .id(1L).description("description")
                        .build())
                .owner(User.builder().id(1).name("User").build())
                .available(false)
                .build();
        items.add(item);
        when(itemService.getAllItem())
                .thenReturn(items);

        this.mockMvc
                .perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteItemById() throws Exception {
        this.mockMvc
                .perform(delete("/items/1")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void addComment() throws Exception {
        String json = "{\"text\": \"Add comment from user1\"}";

        Comment comment = Comment.builder().id(1L)
                .item(Item.builder().id(1L).name("item").build())
                .text("Add comment from user1")
                .author(User.builder().id(1).name("User").build())
                .created(LocalDate.now())
                .build();

        when(itemService.addComment(anyLong(), anyLong(), any()))
                .thenReturn(comment);

        this.mockMvc
                .perform(post("/items/1/comment")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }
}
