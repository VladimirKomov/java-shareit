package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.request.ItemRequestMapper.MAP_REQUEST;

@WebMvcTest(ItemRequestController.class)
public class ItemRequestControllerTest {

    ItemRequest itemRequest;
    ItemRequestDtoResponse response;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemRequestService itemRequestService;
    @MockBean
    private ItemService itemService;

    //ItemRequestDto response;

    @Test
    public void getItemRequestByOwner() throws Exception {
        Collection<ItemRequestDtoResponse> itemRequests = new ArrayList<>();
        itemRequest = ItemRequest.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(User.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .build();
        itemRequests.add(MAP_REQUEST.toItemRequestDtoResponse(itemRequest));

        when(itemRequestService.getRequestsByRequestorId(anyLong()))
                .thenReturn(itemRequests);

        this.mockMvc
                .perform(get("/requests")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getItemRequestById() throws Exception {
        itemRequest = ItemRequest.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(User.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .build();
        when(itemRequestService.getRequestsByRequestId(anyLong(), anyLong()))
                .thenReturn(MAP_REQUEST.toItemRequestDtoResponse(itemRequest));

        this.mockMvc
                .perform(get("/requests/1")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void addItemRequest() throws Exception {
        String json = "{\"description\":\"Хотел бы воспользоваться щёткой для обуви\"}";

        ItemRequestDtoResponse response1 = ItemRequestDtoResponse.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(ItemRequestDtoResponse.Requestor.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .items(List.of())
                .build();


        when(itemRequestService.create(anyLong(), any()))
                .thenReturn(response1);

        this.mockMvc
                .perform(post("/requests")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().is5xxServerError());
    }


    @Test
    public void getAllItemRequest() throws Exception {
        Collection<ItemRequestDtoResponse> itemRequests = new ArrayList<>();

        itemRequest = ItemRequest.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(User.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .build();
        itemRequests.add(MAP_REQUEST.toItemRequestDtoResponse(itemRequest));
        when(itemRequestService.getAll(anyLong(), anyInt(), anyInt()))
                .thenReturn(itemRequests);

        this.mockMvc
                .perform(get("/requests")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk());
    }
}
