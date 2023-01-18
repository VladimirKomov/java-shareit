package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.user.UserMapper.MAP_USER;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository storage) {
        this.repository = storage;
    }

    public UserDto create(UserDto data) {
        //validate(data);
        UserDto userDto = MAP_USER.toUserDto(repository.save(MAP_USER.toUser(data)));
        return userDto;
    }

    public UserDto get(long id) {
        return MAP_USER.toUserDto(repository.findById(id).orElseThrow(NotFoundException::new));
    }

    public UserDto update(long id, UserDto data) {
        data = updateValues(id, data);
        repository.save(MAP_USER.toUser(data));
        return data;
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public List<UserDto> getAll() {
        return repository.findAll().stream()
                .map(MAP_USER::toUserDto)
                .collect(Collectors.toList());
    }

    public int getSize() {
        //return storage.getSize();
        return 0;
    }

    protected UserDto updateValues(long id, UserDto data) {
        //if (data.getEmail() != null) validate(data);
        var target = MAP_USER.toUserDto(repository.findById(id).orElseThrow(NotFoundException::new));
        MAP_USER.update(data, target);

        return target;
    }

//    protected void validate(UserDto data) {
//        if (repository.findAll().contains(MAP_USER.toUser(data))) {
//            throw new ValidationException("User already exists");
//        }
//    }

}
