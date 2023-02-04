package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.item.dto.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.dto.ItemDtoResponseLong;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.resources.Common;
import ru.practicum.shareit.user.User;

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
import static ru.practicum.shareit.booking.BookingMapper.MAP_BOOKING;
import static ru.practicum.shareit.item.CommentMapper.MAP_COMMENT;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    private static final String FILE_PATH = "src/test/java/ru/practicum/shareit/resources/itemJson/";
    ItemDtoResponse itemDtoResponse;
    ItemDtoResponseLong itemDtoResponseLong;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;

    @Test
    public void addItem() throws Exception {
        String json = Common.getFile(FILE_PATH + "addItem.json");

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
        String json = Common.getFile(FILE_PATH + "updateItem.json");

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

        Booking booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(StatusBooking.WAITING)
                .item(Item.builder().id(1).available(true).owner(
                                User.builder().id(2).name("owner").email("owner@owner.com").build())
                        .build())
                .booker(User.builder().id(1).name("name").email("user@user.com").build())
                .build();

        itemDtoResponseLong = new ItemDtoResponseLong(1, "ДрельUpdate", "Простая дрель update", false,
                MAP_BOOKING.toBookingDtoRepository(booking), null, List.of(), 1);
        items.add(itemDtoResponseLong);
        when(itemService.getAllItemByUserId(anyLong(), anyInt(), anyInt()))
                .thenReturn(items);

        this.mockMvc
                .perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void searchBySubstring() throws Exception {
        Collection<ItemDtoResponse> items = new ArrayList<>();
        itemDtoResponse = new ItemDtoResponse(
                1, "ДрельUpdate", "Аккумуляторная дрель update", false, 1);
        items.add(itemDtoResponse);
        when(itemService.getBySubstring(any(), anyInt(), anyInt()))
                .thenReturn(items);

        this.mockMvc
                .perform(get("/items/search")
                        .param("text", "aККум")
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
        String json = Common.getFile(FILE_PATH + "addComment.json");

        Comment comment = Comment.builder()
                .id(1)
                .text("Add comment from user1")
                .item(null)
                .user(User.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .build();

        CommentDtoResponse commentDtoResponse = MAP_COMMENT.toDtoResponse(comment);

        when(itemService.create(anyLong(), anyLong(), any()))
                .thenReturn(commentDtoResponse);

        this.mockMvc
                .perform(post("/items/1/comment")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

}
