package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;


import static ru.practicum.shareit.user.UserMapper.MAP_USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public User create(UserDto data) {
        //validate(data);
        return repository.save(MAP_USER.toUser(data));
    }

    private void validate(UserDto user) {
        if (repository.findByEmail(user.getEmail()).size() > 0) throw new ValidationException();
    }

    public User get(long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public User update(long id, UserDto data) {
        return repository.save(updateValues(id, data));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public List<User> getAll() {
        return new ArrayList<>(repository.findAll());
    }

    public int getSize() {
        return 0;
    }

    protected User updateValues(long id, UserDto data) {
        var target = repository.findById(id).orElseThrow(NotFoundException::new);
        //validate(data);
        MAP_USER.update(MAP_USER.toUser(data), target);

        return target;
    }

}
