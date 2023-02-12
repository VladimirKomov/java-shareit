package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;

import static ru.practicum.shareit.user.UserMapper.MAP_USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserDto create(UserDto data) {
        return MAP_USER.toUserDto(repository.save(MAP_USER.toUser(data)));
    }

    public UserDto get(long id) {
        return MAP_USER.toUserDto(repository.findById(id).orElseThrow(() -> new NotFoundException("user id=" + id)));
    }

    public UserDto update(long id, UserDto data) {
        return MAP_USER.toUserDto(repository.save(updateValues(id, data)));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public User getEntity(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("user id=" + id));
    }

    public Collection<UserDto> getAll() {
        return MAP_USER.toCollectionUserDto(repository.findAll());
    }

    protected User updateValues(long id, UserDto data) {
        var target = repository.findById(id).orElseThrow(() -> new NotFoundException("user id=" + id));
        MAP_USER.update(MAP_USER.toUser(data), target);

        return target;
    }

}
