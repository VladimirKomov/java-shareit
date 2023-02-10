package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    /**
     * Создаёт объект бронирования
     */
    @PostMapping
    public ResponseEntity<Object> addBooking(
            @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @RequestBody @Valid BookItemRequestDto requestDto) {
        log.info("Creating booking {}, userId={}", requestDto, userId);
        return bookingClient.bookItem(userId, requestDto);
    }

    /**
     * Обновляет статус бронирования bookingId владельца вещи userId
     */
    @PatchMapping("{bookingId}")
    public ResponseEntity<Object> approveBooking(
            @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @Positive @PathVariable Long bookingId,
            @RequestParam(value = "approved") final Boolean approved) {
        log.info("Get booking with userId={}, bookingId={}, approved={}", userId, bookingId, approved);
        return bookingClient.update(userId, bookingId, approved);
    }

    /**
     * Возвращает информацию о брони по bookingId для определенного userId пользователя или владельца вещи
     */
    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(
            @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @PathVariable @Min(0) long bookingId) {
        log.info("Get booking {}, userId={}", bookingId, userId);
        return bookingClient.getBooking(userId, bookingId);
    }

    /**
     * Возвращает список всех бронирований для определенного userId пользователя
     */
    @GetMapping
    public ResponseEntity<Object> getUserBookings(
            @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @RequestParam(name = "state", defaultValue = "all") String state,
            @Validated @Min(0) @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Validated @Min(1) @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get booking with state {}, userId={}, from={}, size={}", state, userId, from, size);
        return bookingClient.getBookings(userId, state, from, size);
    }

    /**
     * Возвращает список всех бронирований для определенного userId пользователя - владельца вещи
     */
    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingsAllOrByStateForOwner(
            @RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
            @RequestParam(name = "state", defaultValue = "all") String state,
            @Validated @Min(0) @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Validated @Min(1) @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get booking with state {}, userId={}, from={}, size={}", state, userId, from, size);
        return bookingClient.getByOwner(userId, state, from, size);
    }

}
