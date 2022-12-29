package ru.practicum.shareit.item;

import lombok.Data;
import ru.practicum.shareit.booking.model.BaseModel;
import ru.practicum.shareit.user.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item extends BaseModel {
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private String request;
}
