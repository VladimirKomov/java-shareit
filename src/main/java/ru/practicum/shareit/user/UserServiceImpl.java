package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.model.Storage;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Storage<User> storage;
    private long generateId = 0L;

    @Autowired
    public UserServiceImpl(Storage<User> storage) {
        this.storage = storage;
    }

    public UserDto create(UserDto data) {
        validate(data);
        data.setId(++generateId);
        storage.create(UserMapper.toUser(data));
        return data;
    }

    public UserDto get(long id) {
        return UserMapper.toUserDto(storage.get(id).orElseThrow(NotFoundException::new));
    }

    public UserDto update(long id, UserDto data) {
        data = updateValues(id, data);
        storage.update(UserMapper.toUser(data));
        return data;
    }

    public void delete(long id) {
        storage.delete(id);
    }

    public List<UserDto> getAll() {
        return storage.getAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public int getSize() {
        return storage.getSize();
    }

    protected UserDto updateValues(long id, UserDto data) {
        if (data.getEmail() != null) validate(data);

        var recipient = UserMapper.toUserDto(storage.get(id).orElseThrow(NotFoundException::new));
        if (data.getName() != null) recipient.setName(data.getName());
        if (data.getEmail() != null) recipient.setEmail(data.getEmail());
        return recipient;
    }

    protected void validate(UserDto data) {
        if (storage.getAll().contains(UserMapper.toUser(data))) {
            throw new ValidationException("User already exists");
        }
    }

}
