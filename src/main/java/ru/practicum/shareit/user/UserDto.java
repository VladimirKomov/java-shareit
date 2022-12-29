package ru.practicum.shareit.user;

import lombok.Data;
import ru.practicum.shareit.booking.model.BaseModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Data
public class UserDto extends BaseModel{

    private String name;
    @Email
    @NotBlank
    private String email;


    public UserDto(long id, String name, String email) {
        setId(id);
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        if (!super.equals(o)) return false;
        UserDto userDto = (UserDto) o;
        return email.equals(userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }
}
