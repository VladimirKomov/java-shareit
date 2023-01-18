package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
public class BookingDtoRequest {
    private long itemId;
    private LocalDateTime start;
    private LocalDateTime end;
}
