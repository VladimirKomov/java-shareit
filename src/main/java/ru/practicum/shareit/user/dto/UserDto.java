package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.common.Create;
import ru.practicum.shareit.common.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private long id;
    private String name;
    @Email(groups = {Create.class, Update.class})
    @NotBlank(groups = {Create.class})
    private String email;

}
