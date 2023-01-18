package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingService {

    BookingDtoResponse create(long userId, BookingDtoRequest bookingDto);

    BookingDtoResponse approve(long ownerId, long bookingId, boolean approved);

    BookingDtoResponse getById(long bookingId);

    BookingDtoResponse requestBookingByIdByOwnerBookingOrItem(long bookingId, long userId);

    Collection<BookingDtoResponse> getUserBookings(long userId, String state);

    Collection<BookingDtoResponse> getItemBookingsForOwner(long userId, String state);

    boolean isUserEqualsOwnerBooking(long userId, long bookingId);

    BookingDtoResponse getLastItemBooking(long itemId, LocalDateTime now);

    BookingDtoResponse getNextItemBooking(long itemId, LocalDateTime now);

}
