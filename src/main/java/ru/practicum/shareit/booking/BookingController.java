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
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.BookingMapper.MAP_BOOKING;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDtoResponse addBooking(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                         @RequestBody BookingDtoRequest bookingDto) {
        log.info("Create {} by userId={}", bookingDto.toString(), userId);
        return bookingService.create(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResponse approveBooking(@RequestHeader("X-Sharer-User-Id") @Min(0) long ownerId,
                                             @Min(0) @PathVariable long bookingId,
                                             @RequestParam boolean approved) {
        log.info("Approve bookingId={} for ownerId={}", bookingId, ownerId);
        return bookingService.approve(ownerId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoResponse getBookingById(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                             @Min(0) @PathVariable long bookingId) {
        log.info("GET BookingId={} for userId={}", bookingId, userId);
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public Collection<BookingDtoResponse> getUserBookings(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                                          @RequestParam(defaultValue = "ALL") String state) {
        log.info("GET all Bookings for userId={}", userId);
        return bookingService.getBookingsByBooker(userId, state);
    }

    @GetMapping("/owner")
    public Collection<BookingDtoResponse> getItemBookingsByOwner(@RequestHeader("X-Sharer-User-Id") @Min(0) long userId,
                                                                 @RequestParam(defaultValue = "ALL") String state) {
        log.info("GET all Bookings for ownerId={}", userId);
        return bookingService.getItemBookingsByOwner(userId, state);
    }


}
