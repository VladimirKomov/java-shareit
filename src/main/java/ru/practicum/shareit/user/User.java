package ru.practicum.shareit.user;

import lombok.*;
import ru.practicum.shareit.model.BaseModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class User extends BaseModel {

    private String name;
    @Email
    @NotBlank
    private String email;

    public User(long id, String name, String email) {
        setId(id);
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
