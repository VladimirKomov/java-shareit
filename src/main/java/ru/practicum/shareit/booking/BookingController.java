package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.constraints.Min;
import java.util.Collection;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    /**
     * Создаёт объект бронирования
     */
    @PostMapping
    public BookingDtoResponse addBooking(
            @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @RequestBody BookingDtoRequest bookingDto) {
        log.info("Create {} by userId={}", bookingDto.toString(), userId);
        return bookingService.create(userId, bookingDto);
    }

    /**
     * Обновляет статус бронирования bookingId владельца вещи userId
     */
    @PatchMapping("/{bookingId}")
    public BookingDtoResponse approveBooking(@RequestHeader("X-Sharer-User-Id") @Min(0) long ownerId,
                                             @Min(0) @PathVariable long bookingId,
                                             @RequestParam boolean approved) {
        log.info("Approve bookingId={} for ownerId={}", bookingId, ownerId);
        return bookingService.approve(ownerId, bookingId, approved);
    }

    /**
     * Возвращает информацию о брони по bookingId для определенного userId пользователя или владельца вещи
     */
    @GetMapping("/{bookingId}")
    public BookingDtoResponse getBookingById(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                             @Min(0) @PathVariable long bookingId) {
        log.info("GET BookingId={} for userId={}", bookingId, userId);
        return bookingService.getBookingById(userId, bookingId);
    }

    /**
     * Возвращает список всех бронирований для определенного userId пользователя
     */
    @GetMapping
    public Collection<BookingDtoResponse> getUserBookings(
            @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @RequestParam(defaultValue = "ALL") String state,
            @Validated @Min(0) @RequestParam(defaultValue = "0") int from,
            @Validated @Min(1) @RequestParam(defaultValue = "10") int size) {
        log.info("GET all Bookings for userId={}", userId);
        return bookingService.getBookingsByBooker(userId, state, from, size);
    }

    /**
     * Возвращает список всех бронирований для определенного userId пользователя - владельца вещи
     */
    @GetMapping("/owner")
    public Collection<BookingDtoResponse> getItemBookingsByOwner(
            @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @RequestParam(defaultValue = "ALL") String state,
            @Validated @Min(0) @RequestParam(defaultValue = "0") int from,
            @Validated @Min(1) @RequestParam(defaultValue = "10") int size) {
        log.info("GET all Bookings for ownerId={}", userId);
        return bookingService.getItemBookingsByOwner(userId, state, from, size);
    }


}
