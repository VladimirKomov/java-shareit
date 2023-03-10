package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDtoResponse;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
public class ItemRequestDtoResponse {
    private long id;
    private String description;
    private Requestor requestor;
    private Collection<ItemDtoResponse> items;
    private LocalDateTime created;

    @Data
    @Builder
    public static class Requestor {
        private final long id;
        private final String name;
    }
}
