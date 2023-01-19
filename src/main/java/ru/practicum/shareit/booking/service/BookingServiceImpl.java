package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.practicum.shareit.booking.BookingMapper.MAP_BOOKING;
import static ru.practicum.shareit.item.ItemMapper.MAP_ITEM;
import static ru.practicum.shareit.user.UserMapper.MAP_USER;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    public BookingDtoResponse create(long userId, BookingDtoRequest bookingDto) {
        Booking booking = MAP_BOOKING.toBooking(bookingDto);
        booking.setBooker(MAP_USER.toUser(userService.get(userId)));
        booking.setItem(MAP_ITEM.toItem(itemService.get(bookingDto.getItemId())));
//        if (!booking.getItem().getAvailable()) {
//            throw new BadRequestException("Недоступно для бронирования");
//        }
        validation(booking);

        booking.setStatus(StatusBooking.WAITING);
        bookingRepository.save(booking);
        return MAP_BOOKING.toBookingDtoResponse(booking);

    }

    @Override
    public BookingDtoResponse approve(long ownerId, long bookingId, boolean approved) {
        return null;
    }

    @Override
    public BookingDtoResponse getById(long bookingId) {
        return null;
    }

    @Override
    public BookingDtoResponse requestBookingByIdByOwnerBookingOrItem(long bookingId, long userId) {
        return null;
    }

    @Override
    public Collection<BookingDtoResponse> getUserBookings(long userId, String state) {
        return null;
    }

    @Override
    public Collection<BookingDtoResponse> getItemBookingsForOwner(long userId, String state) {
        return null;
    }

    @Override
    public boolean isUserEqualsOwnerBooking(long userId, long bookingId) {
        return false;
    }

    @Override
    public BookingDtoResponse getLastItemBooking(long itemId, LocalDateTime now) {
        return null;
    }

    @Override
    public BookingDtoResponse getNextItemBooking(long itemId, LocalDateTime now) {
        return null;
    }

    private void validation(Booking booking) {
        if (!booking.getItem().getAvailable()) {
            throw new BadRequestException("Not available for booking");
        }
        if (booking.getStart().isAfter(booking.getEnd())
                || booking.getStart().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("The time is incorrect");
        }
    }
}
