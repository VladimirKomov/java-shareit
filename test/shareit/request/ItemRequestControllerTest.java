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
import ru.practicum.shareit.resources.Common;
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

    private static final String FILE_PATH = "src/test/java/ru/practicum/shareit/resources/requestJson/";

    ItemRequest itemRequest;
    ItemRequestDtoResponse response;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemRequestService itemRequestService;
    @MockBean
    private ItemService itemService;

    @Test
    public void getItemRequestByOwner() throws Exception {
        Collection<ItemRequestDtoResponse> itemRequests = new ArrayList<>();
        itemRequest = ItemRequest.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(User.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .build();
        itemRequests.add(ItemRequestMapper.MAP_REQUEST.toItemRequestDtoResponse(itemRequest));

        Mockito.when(itemRequestService.getRequestsByRequestorId(ArgumentMatchers.anyLong()))
                .thenReturn(itemRequests);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/requests")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getItemRequestById() throws Exception {
        itemRequest = ItemRequest.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(User.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .build();
        Mockito.when(itemRequestService.getRequestsByRequestId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(ItemRequestMapper.MAP_REQUEST.toItemRequestDtoResponse(itemRequest));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/requests/1")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    public void addItemRequest() throws Exception {
        String json = Common.getFile(FILE_PATH + "addItemRequest.json");

        ItemRequestDtoResponse response = ItemRequestDtoResponse.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(ItemRequestDtoResponse.Requestor.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .items(List.of())
                .build();

        Mockito.when(itemRequestService.create(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                .thenReturn(response);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/requests")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    public void getItemRequest() throws Exception {
        Collection<ItemRequestDtoResponse> itemRequests = new ArrayList<>();

        itemRequest = ItemRequest.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(User.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .build();

        ItemRequestDtoResponse response = ItemRequestDtoResponse.builder()
                .id(1)
                .description("Хотел бы воспользоваться щёткой для обуви")
                .requestor(ItemRequestDtoResponse.Requestor.builder().id(1).name("User").build())
                .created(LocalDateTime.now())
                .items(List.of())
                .build();


        itemRequests.add(ItemRequestMapper.MAP_REQUEST.toItemRequestDtoResponse(itemRequest));
        Mockito.when(itemRequestService.getAll(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                .thenReturn(itemRequests);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/requests")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
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
        itemRequests.add(ItemRequestMapper.MAP_REQUEST.toItemRequestDtoResponse(itemRequest));
        Mockito.when(itemRequestService.getAll(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                .thenReturn(itemRequests);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/requests/all")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
