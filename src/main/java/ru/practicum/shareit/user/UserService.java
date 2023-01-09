package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(long id, UserDto userDto);

    UserDto get(long id);

    Collection<UserDto> getAll();

    void delete(long id);

    int getSize();
}
