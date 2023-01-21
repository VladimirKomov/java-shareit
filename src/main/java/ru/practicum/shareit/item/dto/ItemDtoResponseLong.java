package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoRepository;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemDtoResponseLong {
    private long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private BookingDtoRepository lastBooking;
    private BookingDtoRepository nextBooking;

}
