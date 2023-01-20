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
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.BookingMapper.MAP_BOOKING;


@Slf4j
//@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;
    @PostMapping
    public BookingDtoResponse addBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @RequestBody BookingDtoRequest bookingDto) {
        log.info("Create {} by userId={}", bookingDto.toString(), userId);
        return MAP_BOOKING.toBookingDtoResponse(bookingService.create(userId, bookingDto));
    }

    // Может быть выполнено владельцем вещи
    @PatchMapping("/{bookingId}")
    public BookingDtoResponse approveBooking(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                        @PathVariable long bookingId,
                                        @RequestParam boolean approved) {
        return MAP_BOOKING.toBookingDtoResponse(bookingService.approve(ownerId, bookingId, approved));
    }

    //     Может быть выполнено либо автором бронирования, либо владельцем вещи
    @GetMapping("/{bookingId}")
    public BookingDtoResponse getBookingById(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @PathVariable long bookingId) {
        return MAP_BOOKING.toBookingDtoResponse(bookingService.getBookingById(userId, bookingId));
    }

    @GetMapping
    public Collection<BookingDtoResponse> getUserBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                               @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getBookingsByBooker(userId, state).stream()
                .map(MAP_BOOKING::toBookingDtoResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner")
    public Collection<BookingDtoResponse> getItemBookingsByOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                       @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getItemBookingsByOwner(userId, state).stream()
                .map(MAP_BOOKING::toBookingDtoResponse)
                .collect(Collectors.toList());
    }


}
