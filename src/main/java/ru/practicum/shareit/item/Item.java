package ru.practicum.shareit.item;

import lombok.Data;
import ru.practicum.shareit.model.BaseModel;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item extends BaseModel {
    private String name;
    private String description;
    @NotNull
    private Boolean available;
    private User owner;
    private String request;

    public Item(long id, String name, String description, Boolean available, User owner, String request) {
        setId(id);
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.request = request;
    }
}
