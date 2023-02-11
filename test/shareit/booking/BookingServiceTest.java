package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ru.practicum.shareit.booking.BookingMapper.MAP_BOOKING;

@SpringBootTest
public class BookingServiceTest {
    Booking booking;
    BookingDtoRequest request;
    Item item;
    User user;
    User owner;
    @Autowired
    private BookingService service;
    @MockBean
    private UserService userService;
    @MockBean
    private BookingRepository repository;
    @MockBean
    private ItemService itemService;
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
        Mockito.when(itemService.getEntity(1)).thenReturn(item);
        Mockito.when(userService.getEntity(1)).thenReturn(user);
        Mockito.when(userService.getEntity(2)).thenReturn(owner);
        service.create(1, request);
        booking = BookingMapper.MAP_BOOKING.toBooking(request);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStatus(StatusBooking.WAITING);
        Mockito.verify(repository, Mockito.times(1)).save(booking);
    }

    @Test
    void approveBooking() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(StatusBooking.WAITING)
                .item(item)
                .build();
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(booking));
        service.approve(2, 1, true);
        Mockito.verify(repository, Mockito.times(1)).save(booking);
    }

    @Test
    void requestBookingByIdByOwnerBookingOrItem() {
        booking = Booking.builder().id(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1)).status(StatusBooking.WAITING)
                .item(item)
                .booker(user)
                .build();
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(booking));

        service.getBookingById(1, 1);
        Mockito.verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    void addBookingNot() {
        request = BookingDtoRequest.builder()
                .itemId(1)
                .start(LocalDateTime.now().plusMinutes(2))
                .end(LocalDateTime.now().plusDays(1))
                .build();
        Mockito.when(itemService.getEntity(1)).thenReturn(item);
        Mockito.when(userService.getEntity(3)).thenThrow(NotFoundException.class);
        Mockito.verify(repository, Mockito.times(0)).save(booking);
        Assertions.assertThrows(NotFoundException.class, () -> service.create(3, request));
    }

    @Test
    void getUserBookings_ALL() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "ALL", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByBookerIdOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any());
    }

    @Test
    void getUserBookings_ALLLLL() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        Assertions.assertThrows(BadRequestException.class, () ->
                service.getBookingsByBooker(1, "ALLLLL", 0, 1));
        Mockito.verify(repository, Mockito.times(0))
                .findAllByBookerIdOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any());
    }

    @Test
    void getUserBookings_CURRENT() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "CURRENT", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any());
    }

    @Test
    void getUserBookings_PAST() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "PAST", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByBookerIdAndEndBeforeOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any());
    }

    @Test
    void getUserBookings_FUTURE() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "FUTURE", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByBookerIdAndStartAfterOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any());
    }

    @Test
    void getUserBookings_Waiting() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "WAITING", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByBookerIdAndStatusOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void getUserBookings_REJECTED() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getBookingsByBooker(1, "REJECTED", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByBookerIdAndStatusOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void getItemBookingsByOwner_ALL() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "ALL", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByItemOwnerIdOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any());
    }

    @Test
    void getItemBookingsByOwner_ALLLLL() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        Assertions.assertThrows(BadRequestException.class, () ->
                service.getItemBookingsByOwner(1, "ALLLLL", 0, 1));
        Mockito.verify(repository, Mockito.times(0))
                .findAllByItemOwnerIdOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any());
    }

    @Test
    void getItemBookingsByOwner_CURRENT() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "CURRENT", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any(LocalDateTime.class),
                        ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any());
    }

    @Test
    void getItemBookingsByOwner_PAST() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "PAST", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByItemOwnerIdAndEndIsBeforeOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any());
    }

    @Test
    void getItemBookingsByOwner_FUTURE() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "FUTURE", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByItemOwnerIdAndStartIsAfterOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any());
    }

    @Test
    void getItemBookingsByOwner_WAITING() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "WAITING", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByItemOwnerIdAndStatusOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void getItemBookingsByOwner_REJECTED() {
        Mockito.when(userService.getEntity(1L)).thenReturn(user);

        service.getItemBookingsByOwner(1, "REJECTED", 0, 1);
        Mockito.verify(repository, Mockito.times(1))
                .findAllByItemOwnerIdAndStatusOrderByStartDesc(ArgumentMatchers.anyLong(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }


}
