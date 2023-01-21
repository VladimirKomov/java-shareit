package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.StateException;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;

import static ru.practicum.shareit.booking.BookingMapper.MAP_BOOKING;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    public Booking create(long userId, BookingDtoRequest bookingDto) {
        Booking booking = MAP_BOOKING.toBooking(bookingDto);
        booking.setBooker(userService.get(userId));
        booking.setItem(itemService.getEntity(bookingDto.getItemId()));
//        if (!booking.getItem().getAvailable()) {
//            throw new BadRequestException("Недоступно для бронирования");
//        }
        validation(booking);

        booking.setStatus(StatusBooking.WAITING);
        //bookingRepository.save(booking);
        return bookingRepository.save(booking);

    }

    @Override
    public Booking approve(long ownerId, long bookingId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
        if (booking.getItem().getOwner().getId() != ownerId) throw new NotFoundException();
        if (booking.getStatus() == StatusBooking.APPROVED) throw new BadRequestException("Status incorrect");
        if (approved) {
            booking.setStatus(StatusBooking.APPROVED);
        } else {
            booking.setStatus(StatusBooking.REJECTED);
        }
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getById(long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
    }

    @Override
    public Booking getBookingById(long userId, long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
        if (booking.getBooker().getId() != userId && booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException();
        }
        return booking;
//
//        return bookingRepository.findByIdAndAndBookerIdOrItemOwnerId(bookingId, userId, userId)
//                .orElseThrow(NotFoundException::new);

    }

    @Override
    public Collection<Booking> getBookingsByBooker(long userId, String state) {
        userService.get(userId);
        switch (state) {
            case "ALL":
                return bookingRepository.findAllByBookerIdOrderByStartDesc(userId);
            case "CURRENT":
                LocalDateTime now = LocalDateTime.now();
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
    public Collection<Booking> getItemBookingsByOwner(long userId, String state) {
        userService.get(userId);
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

    @Override
    public boolean isUserEqualsOwnerBooking(long userId, long bookingId) {
        return false;
    }

    @Override
    public Booking getLastItemBooking(long itemId, LocalDateTime now) {
//        return bookingRepository.findLastItemBooking (itemId, now).stream()
//                .max(Comparator.comparing(Booking::getEnd))
//                .orElse(null);
        return null;
    }

    @Override
    public Booking getNextItemBooking(long itemId, LocalDateTime now) {
//        return bookingRepository.findNextItemBooking(itemId, now).stream()
//                .min(Comparator.comparing(Booking::getStart))
//                .orElse(null);
        return null;
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
//        if (bookingRepository.findByBrookerIdItemIdTime(booking.getBooker().getId(),
//                booking.getItem().getId(), booking.getStart()).size() > 0) {
//            throw new NotFoundException();
//        }
//        if (bookingRepository.findByItemIdAndTime(booking.getItem().getId(), booking.getStart()).size() > 0) {
//            throw new NotFoundException();
//        }
//        if (bookingRepository.findByBookerIdAndTime(booking.getBooker().getId(), booking.getStart()).size() > 0) {
//            throw new NotFoundException();
//        }
    }
}
