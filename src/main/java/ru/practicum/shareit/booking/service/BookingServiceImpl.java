package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.practicum.shareit.booking.BookingMapper.MAP_BOOKING;
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
        booking.setBooker(userService.getEntity(userId));
        booking.setItem(itemService.getEntity(bookingDto.getItemId()));
        validation(booking);
        booking.setStatus(StatusBooking.WAITING);
        return MAP_BOOKING.toBookingDtoResponse(bookingRepository.save(booking));

    }

    @Override
    public BookingDtoResponse approve(long ownerId, long bookingId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
        if (booking.getItem().getOwner().getId() != ownerId) throw new NotFoundException();
        if (booking.getStatus() == StatusBooking.APPROVED) throw new BadRequestException("Status incorrect");
        if (approved) {
            booking.setStatus(StatusBooking.APPROVED);
        } else {
            booking.setStatus(StatusBooking.REJECTED);
        }
        return MAP_BOOKING.toBookingDtoResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingDtoResponse getBookingById(long userId, long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
        if (booking.getBooker().getId() != userId && booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException();
        }
        return MAP_BOOKING.toBookingDtoResponse(booking);
    }

    @Override
    public Collection<BookingDtoResponse> getBookingsByBooker(long userId, String state) {
        userService.get(userId);
        return MAP_BOOKING.toCollectionBookingDtoResponse(findBookingsByBooker(userId, state));

    }

    private Collection<Booking> findBookingsByBooker(long userId, String state) {
        switch (state) {
            case "ALL":
                return bookingRepository.findAllByBookerIdOrderByStartDesc(userId);
            case "CURRENT":
                return bookingRepository.findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(userId, LocalDateTime.now(),
                        LocalDateTime.now());
            case "PAST":
                return bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
            case "FUTURE":
                return bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
            case "WAITING":
                return bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, StatusBooking.WAITING);
            case "REJECTED":
                return bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, StatusBooking.REJECTED);
            default:
                throw new BadRequestException("Unknown state: " + state);
        }
    }

    @Override
    public Collection<BookingDtoResponse> getItemBookingsByOwner(long userId, String state) {
        userService.get(userId);
        return MAP_BOOKING.toCollectionBookingDtoResponse(findBookingsByOwner(userId, state));
    }

    private Collection<Booking> findBookingsByOwner(long userId, String state) {
        switch (state) {
            case "ALL":
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId);
            case "CURRENT":
                return bookingRepository.findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(userId, LocalDateTime.now(),
                        LocalDateTime.now());
            case "PAST":
                return bookingRepository.findAllByItemOwnerIdAndEndIsBeforeOrderByStartDesc(userId, LocalDateTime.now());
            case "FUTURE":
                return bookingRepository.findAllByItemOwnerIdAndStartIsAfterOrderByStartDesc(userId, LocalDateTime.now());
            case "WAITING":
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, StatusBooking.WAITING);
            case "REJECTED":
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, StatusBooking.REJECTED);
            default:
                throw new BadRequestException("Unknown state: " + state);
        }
    }

    private void validation(Booking booking) {
        if (!booking.getItem().getAvailable()) {
            throw new BadRequestException("Not available for booking");
        }
        if (booking.getStart().isAfter(booking.getEnd())
                || (booking.getStart().isBefore(LocalDateTime.now()))) {
            throw new BadRequestException("Time incorrect");
        }

        if (!booking.getItem().getAvailable()) {
            throw new BadRequestException("Item incorrect");
        }
        if (booking.getBooker().getId() == booking.getItem().getOwner().getId()) {
            throw new NotFoundException();
        }
    }
}
