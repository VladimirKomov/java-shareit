package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingService {

    Booking create(long userId, BookingDtoRequest bookingDto);

    Booking approve(long ownerId, long bookingId, boolean approved);

    Booking getById(long bookingId);

    Booking getBookingById(long bookingId, long userId);

    Collection<Booking> getBookingsByBooker(long userId, String state);

    Collection<Booking> getItemBookingsByOwner(long userId, String state);

    boolean isUserEqualsOwnerBooking(long userId, long bookingId);

    Booking getLastItemBooking(long itemId, LocalDateTime now);

    Booking getNextItemBooking(long itemId, LocalDateTime now);

}
