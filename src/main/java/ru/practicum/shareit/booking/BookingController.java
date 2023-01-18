package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collection;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;
    @PostMapping
    public BookingDtoResponse addBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @RequestBody BookingDtoRequest bookingDto) {
        log.info("Create {} by userId={}", bookingDto.toString(), userId);
        return bookingService.create(userId, bookingDto);
    }

    // Может быть выполнено владельцем вещи
    @PatchMapping("/{bookingId}")
    public BookingDtoResponse approveBooking(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                        @PathVariable long bookingId,
                                        @RequestParam boolean approved) {
        return bookingService.approve(ownerId, bookingId, approved);
    }

    //     Может быть выполнено либо автором бронирования, либо владельцем вещи
    @GetMapping("/{bookingId}")
    public BookingDtoResponse getBookingById(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @PathVariable long bookingId) {
        return bookingService.requestBookingByIdByOwnerBookingOrItem(bookingId, userId);
    }

    @GetMapping
    public Collection<BookingDtoResponse> getUserBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                               @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDtoResponse> getItemBookingsForOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                       @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getItemBookingsForOwner(userId, state);
    }


}
