package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static ru.practicum.shareit.item.CommentMapper.MAP_COMMENT;
import static ru.practicum.shareit.item.ItemMapper.MAP_ITEM;

@SpringBootTest
public class ItemServiceTest {

    Item item;
    User user;
    Comment comment;
    UserDto userDto;
    ItemDto itemDto;
    CommentDto commentDto;
    Booking booking;
    Collection<Booking> bookings;
    @Autowired
    private ItemService service;
    @MockBean
    private UserService userService;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private BookingRepository bookingRepository;
    @MockBean
    private ItemRequestService itemRequestService;

    @BeforeEach
    void setUp() {
        user = new User(1, "name", "user@user.com");
        userDto = new UserDto(1, "name", "user@user.com");
        booking = Booking.builder().id(1).build();
        bookings = new ArrayList<>();
        bookings.add(booking);
    }


    @Test
    void addItem() {
        itemDto = new ItemDto(1, "Дрель", "Простая дрель", true, null, null, 1);
        service.create(1, itemDto);
        item = MAP_ITEM.toItem(itemDto);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void updateItem() {
        itemDto = new ItemDto(1, "ДрельUpdate", "Простая дрель update", true, userDto, null, 1);
        item = MAP_ITEM.toItem(itemDto);
        item.setOwner(user);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        service.update(1, 1, itemDto);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void getAllItemByUserId() {
        itemDto = new ItemDto(1, "ДрельUpdate", "Простая дрель update", true, userDto, null, 1);
        item = MAP_ITEM.toItem(itemDto);
        List<Item> items = List.of(item);
        when(itemRepository.findItemsByOwnerIdOrderById(
                1L, PageRequest.of(0, 1))).thenReturn(items);
        service.getAllItemByUserId(1, 0, 1);
    }

    @Test
    void getItem() {
        itemDto = new ItemDto(1, "ДрельUpdate", "Простая дрель update", true, userDto, null, 1);
        item = MAP_ITEM.toItem(itemDto);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        service.get(1, 1);
    }

    @Test
    void getBySubstring() {
        itemDto = new ItemDto(1, "ДрельUpdate", "Аккумуляторная дрель update", true, userDto, null, 1);
        item = MAP_ITEM.toItem(itemDto);
        List<Item> items = List.of(item);
        when(itemRepository.searchAvailableByNameAndDescription(
                "аККум", PageRequest.of(0, 1))).thenReturn(items);
        service.getBySubstring("аККум", 0, 1);
    }

    @Test
    void addComment() throws InterruptedException {
        commentDto = new CommentDto("comment");
        itemDto = new ItemDto(1, "ДрельUpdate", "Простая дрель update", true, null, null, 1);

        when(bookingRepository.findAllByBookerIdAndItemIdAndStatusAndStartBeforeOrderByStartDesc(
                anyLong(), anyLong(), any(), any())).thenReturn(bookings);
        item = MAP_ITEM.toItem(itemDto);
        when(itemRepository.findById(1L)).thenReturn(Optional.ofNullable(item));
        service.create(1, 1, commentDto);
        comment = MAP_COMMENT.toComment(commentDto);
        comment.setCreated(LocalDateTime.now());
        verify(commentRepository, times(1)).save(any());
    }

    @Test
    void deleteItem() {
        itemDto = new ItemDto(1, "ДрельUpdate", "Простая дрель update", true, null, null, 1);
        item = MAP_ITEM.toItem(itemDto);
        when(itemRepository.findById(1L)).thenReturn(Optional.ofNullable(item));
        service.delete(1);
        verify(itemRepository, times(1)).deleteById(1L);
    }

}
