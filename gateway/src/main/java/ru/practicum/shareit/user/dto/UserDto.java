package ru.practicum.shareit.user.dto;

import lombok.*;
import ru.practicum.shareit.common.Create;
import ru.practicum.shareit.common.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ToString
@NonNull
@Getter
@Setter
@AllArgsConstructor
public class UserDto implements Serializable {
    private long id;
    private String name;
    @Email(groups = {Create.class, Update.class})
    @NotBlank(groups = {Create.class})
    private String email;
}
