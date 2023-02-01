package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static ru.practicum.shareit.booking.BookingMapper.MAP_BOOKING;

@SpringBootTest
public class BookingServiceTest {
    @Autowired
    private BookingService service;

    @MockBean
    private UserService userService;

    @MockBean
    private BookingRepository repository;

    @MockBean
    private ItemService itemService;

    Booking booking;
    BookingDtoRequest request;
    Item item;
    User user;
    User owner;
    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void upSet() {
        user = User.builder().id(1).name("name").email("user@user.com").build();
        owner = User.builder().id(2).name("owner").email("owner@owner.com").build();
        item = Item.builder()
                .id(1).available(true)
                .owner(owner)
                .build();


    }

    @Test
    void addBooking() {
        request = BookingDtoRequest.builder()
                .itemId(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1))
                .build();
        when(itemService.getEntity(1)).thenReturn(item);
        when(userService.getEntity(1)).thenReturn(user);
        when(userService.getEntity(2)).thenReturn(owner);
        service.create(1, request);
        booking = MAP_BOOKING.toBooking(request);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStatus(StatusBooking.WAITING);
        verify(repository, times(1)).save(booking);
    }

    @Test
    void approveBooking() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(StatusBooking.WAITING)
                .item(item)
                .build();
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(booking));
        //when(itemService.isUserEqualsOwnerItem(anyLong(), anyLong())).thenReturn(true);
        service.approve(2, 1, true);
        verify(repository, times(1)).save(booking);
    }

    @Test
    void requestBookingByIdByOwnerBookingOrItem() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(StatusBooking.WAITING)
                .item(item)
                .booker(user)
                .build();
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(booking));

        service.getBookingById(1, 1);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getUserBookings_ALL() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "ALL", 0, 1);
        verify(repository, times(1)).
                findAllByBookerIdOrderByStartDesc(anyLong(), any());
    }

    @Test
    void getUserBookings_CURRENT() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "CURRENT", 0, 1);
        verify(repository, times(1)).
                findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class), any());
    }

    @Test
    void getUserBookings_PAST() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "PAST", 0, 1);
        verify(repository, times(1)).
                findAllByBookerIdAndEndBeforeOrderByStartDesc(anyLong(), any(LocalDateTime.class), any());
    }

    @Test
    void getUserBookings_FUTURE() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "FUTURE", 0, 1);
        verify(repository, times(1)).
                findAllByBookerIdAndStartAfterOrderByStartDesc(anyLong(), any(LocalDateTime.class), any());
    }

    @Test
    void getUserBookings_Waiting() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "WAITING", 0,1);
        verify(repository, times(1))
                .findAllByBookerIdAndStatusOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getUserBookings_REJECTED() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "REJECTED", 0,1);
        verify(repository, times(1))
                .findAllByBookerIdAndStatusOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getItemBookingsByOwner_ALL() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "ALL", 0,1);
        verify(repository, times(1))
                .findAllByItemOwnerIdOrderByStartDesc(anyLong(), any());
    }

    @Test
    void getItemBookingsByOwner_CURRENT() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "CURRENT", 0,1);
        verify(repository, times(1))
                .findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(anyLong(), any(LocalDateTime.class),
                        any(LocalDateTime.class), any());
    }

    @Test
    void getItemBookingsByOwner_PAST() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "PAST", 0,1);
        verify(repository, times(1))
                .findAllByItemOwnerIdAndEndIsBeforeOrderByStartDesc(anyLong(), any(LocalDateTime.class), any());
    }

    @Test
    void getItemBookingsByOwner_FUTURE() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "FUTURE", 0,1);
        verify(repository, times(1))
                .findAllByItemOwnerIdAndStartIsAfterOrderByStartDesc(anyLong(), any(LocalDateTime.class), any());
    }

    @Test
    void getItemBookingsByOwner_WAITING() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "WAITING", 0,1);
        verify(repository, times(1))
                .findAllByItemOwnerIdAndStatusOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getItemBookingsByOwner_REJECTED() {
        when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "REJECTED", 0,1);
        verify(repository, times(1))
                .findAllByItemOwnerIdAndStatusOrderByStartDesc(anyLong(), any(), any());
    }




}
