package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.practicum.shareit.booking.BookingMapper.MAP_BOOKING;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public BookingDtoResponse addBooking(long userId, BookingDto bookingDto) {
        return MAP_BOOKING.toBookingDtoResponse(
                bookingRepository.save(MAP_BOOKING.toBooking(bookingDto)));
    }

    @Override
    public BookingDtoResponse approveBooking(long ownerId, long bookingId, boolean approved) {
        return null;
    }

    @Override
    public BookingDtoResponse getBookingById(long bookingId) {
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
}
