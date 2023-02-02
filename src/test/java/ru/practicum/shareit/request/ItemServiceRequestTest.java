package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ItemServiceRequestTest {
    @Autowired
    private ItemRequestService service;

    @MockBean
    private UserService userService;

    @MockBean
    private ItemRequestRepository itemRequestRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    void addItemRequest() {
        User user = User.builder().id(1).name("name").email("user@user.com").build();
        ItemRequestDto response = new ItemRequestDto();
        response.setDescription("description");

        when(userService.getEntity(1)).thenReturn(user);
        service.create(1, response);
        verify(itemRequestRepository, times(1)).save(any());
    }

    @Test
    void getItemRequestByOwner() {
        User user = User.builder().id(1).name("name").email("user@user.com").build();
        when(userService.getEntity(1)).thenReturn(user);
        service.getRequestsByRequestorId(1);
        verify(itemRequestRepository, times(1)).findAllByRequestorIdOrderByCreatedDesc(1L);
    }

    @Test
    void getItemRequestById() {
        User user = User.builder().id(1).name("name").email("user@user.com").build();
        ItemRequest itemRequest = ItemRequest.builder()
                .description("description")
                .requestor(user)
                .created(LocalDateTime.now())
                .build();
        ItemRequestDto response = new ItemRequestDto();
        response.setDescription("description");
        service.create(1, response);
        when(userService.getEntity(1)).thenReturn(user);
        when(itemRequestRepository.findById(1L)).thenReturn(Optional.ofNullable(itemRequest));
        service.getRequestsByRequestId(1, 1);
        verify(itemRequestRepository, times(1)).findById(1L);
    }

    @Test
    void getAllItemRequest() {
        User user = User.builder().id(1).name("name").email("user@user.com").build();
        ItemRequest itemRequest = ItemRequest.builder()
                .description("ddddd")
                .requestor(user)
                .created(LocalDateTime.now())
                .build();

        when(userService.getEntity(user.getId())).thenReturn(user);
        when(itemRequestRepository.findAllByRequestorIdIsNotOrderByCreatedDesc(user.getId(),
                PageRequest.of(0, 1)))
                .thenReturn(List.of(itemRequest));

        var result = service.getAll(1, 0, 1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(itemRequest.getDescription(), result.stream().findFirst().get().getDescription());
    }
}
