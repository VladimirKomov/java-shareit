package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
public class BookingDtoRepository {
    private long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private StatusBooking status;
    private long bookerId;
    private long itemId;
}
