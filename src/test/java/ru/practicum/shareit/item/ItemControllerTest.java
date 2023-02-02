package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.dto.ItemDtoResponseLong;
import ru.practicum.shareit.item.service.ItemService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    ItemDtoResponse itemDtoResponse;
    ItemDtoResponseLong itemDtoResponseLong;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;

    @Test
    public void addItem() throws Exception {
        String json = "{\n" +
                "    \"name\": \"Дрель\",\n" +
                "    \"description\": \"Простая дрель\",\n" +
                "    \"available\": true\n" +
                "}";

        itemDtoResponse = new ItemDtoResponse(1, "Дрель", "Простая дрель", true, 1);

        when(itemService.create(anyLong(), any()))
                .thenReturn(itemDtoResponse);

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
                "    \"name\": \"ДрельUpdate\",\n" +
                "    \"description\": \"Аккумуляторная дрель update\",\n" +
                "    \"available\": false\n" +
                "}";

        itemDtoResponse = new ItemDtoResponse(1, "ДрельUpdate", "Простая дрель update", false, 1);

        when(itemService.update(anyLong(), anyLong(), any()))
                .thenReturn(itemDtoResponse);

        this.mockMvc
                .perform(patch("/items/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("ДрельUpdate")));
    }

    @Test
    public void getItemById() throws Exception {
        itemDtoResponseLong = new ItemDtoResponseLong(1, "ДрельUpdate", "Простая дрель update", false,
                null, null, List.of(), 1);

        when(itemService.get(anyLong(), anyLong()))
                .thenReturn(itemDtoResponseLong);

        this.mockMvc
                .perform(get("/items/1")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("ДрельUpdate")));
    }

    @Test
    public void getAllItem() throws Exception {
        Collection<ItemDtoResponseLong> items = new ArrayList<>();
        itemDtoResponseLong = new ItemDtoResponseLong(1, "ДрельUpdate", "Простая дрель update", false,
                null, null, List.of(), 1);
        items.add(itemDtoResponseLong);
        when(itemService.getAllItemByUserId(anyLong(), anyInt(), anyInt()))
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

        CommentDtoResponse commentDtoResponse = CommentDtoResponse.builder()
                .id(1)
                .text("Add comment from user1")
                .authorName("User")
                .created(LocalDateTime.now())
                .build();

        when(itemService.create(anyLong(), anyLong(), any()))
                .thenReturn(commentDtoResponse);

        this.mockMvc
                .perform(post("/items/1/comment")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }
}
