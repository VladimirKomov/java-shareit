package ru.practicum.shareit.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.user.dto.UserDto;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserMapper {

    UserMapper MAP = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
}
