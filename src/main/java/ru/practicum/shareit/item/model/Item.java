package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.booking.abstracts.BaseModel;
import ru.practicum.shareit.user.model.User;

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
