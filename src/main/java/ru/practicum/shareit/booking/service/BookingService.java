package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingService {

    BookingDtoResponse addBooking(long userId, BookingDto bookingDto);

    BookingDtoResponse approveBooking(long ownerId, long bookingId, boolean approved);

    BookingDtoResponse getBookingById(long bookingId);

    BookingDtoResponse requestBookingByIdByOwnerBookingOrItem(long bookingId, long userId);

    Collection<BookingDtoResponse> getUserBookings(long userId, String state);

    Collection<BookingDtoResponse> getItemBookingsForOwner(long userId, String state);

    boolean isUserEqualsOwnerBooking(long userId, long bookingId);

    BookingDtoResponse getLastItemBooking(long itemId, LocalDateTime now);

    BookingDtoResponse getNextItemBooking(long itemId, LocalDateTime now);

}
