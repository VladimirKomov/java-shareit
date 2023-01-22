package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.util.Collection;

public interface BookingService {

    BookingDtoResponse create(long userId, BookingDtoRequest bookingDto);

    BookingDtoResponse approve(long ownerId, long bookingId, boolean approved);

    BookingDtoResponse getBookingById(long bookingId, long userId);

    Collection<BookingDtoResponse> getBookingsByBooker(long userId, String state);

    Collection<BookingDtoResponse> getItemBookingsByOwner(long userId, String state);

}
