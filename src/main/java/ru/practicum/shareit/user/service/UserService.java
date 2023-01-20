package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {

    User create(UserDto userDto);

    User update(long id, UserDto userDto);

    User get(long id);

    Collection<User> getAll();

    void delete(long id);

    int getSize();
}
