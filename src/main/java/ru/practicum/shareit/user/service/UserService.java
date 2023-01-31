package ru.practicum.shareit.user.service;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(long id, UserDto userDto);

    UserDto get(long id);

    Collection<UserDto> getAll();

    void delete(long id);

    User getEntity(long id);
}
