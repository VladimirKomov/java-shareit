package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDtoRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
public class ItemDtoResponseLong {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private BookingDtoRepository lastBooking;
    private BookingDtoRepository nextBooking;
    private Collection<CommentDtoResponse> comments;
    private long requestId;

}
