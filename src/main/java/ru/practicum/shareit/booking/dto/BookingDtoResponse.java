package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.StatusBooking;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDtoResponse {
    private long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private StatusBooking status;
    private User booker;
    private Item item;
}