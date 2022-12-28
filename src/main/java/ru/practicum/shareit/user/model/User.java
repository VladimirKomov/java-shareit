package ru.practicum.shareit.user.model;

import lombok.*;
import ru.practicum.shareit.booking.abstracts.BaseModel;

import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Data
public class User extends BaseModel {
    private String name;
    private String email;

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
