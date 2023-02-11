package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.common.Create;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class ItemDto {
    private long id;
    @NotNull(groups = {Create.class})
    @NotBlank(groups = {Create.class})
    private String name;
    @NotNull(groups = {Create.class})
    @NotBlank(groups = {Create.class})
    private String description;
    @NotNull(groups = {Create.class})
    private Boolean available;
    private UserDto owner;
    private ItemRequestDto request;
    private long requestId;
}
