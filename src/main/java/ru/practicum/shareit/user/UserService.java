package ru.practicum.shareit.user;

import org.apache.el.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Storage;
import ru.practicum.shareit.exception.NotFoundException;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private long generateId = 0L;

    private final Storage<User> storage;

    @Autowired
    public UserService(Storage<User> storage) {
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
        updateValues(id, data);
        storage.update(UserMapper.toUser(data));
        return data;
    }

    public void delete(long id) {
        storage.delete(id);
    }

    public void deleteAll() {
        storage.deleteAll();
    }

    public List<UserDto> getAll() {
        List<UserDto> listUserDto = new ArrayList<>();
        for (User value: storage.getAll()) {
            listUserDto.add(UserMapper.toUserDto(value));
        }
        return  listUserDto;
    }

    public int getSize() {
        return storage.getSize();
    }

    protected void updateValues(long id, UserDto data) {
        if (data.getEmail() != null) {
            validate(data);
        }

        var donor = storage.get(id).orElseThrow(NotFoundException::new);
        data.setId(donor.getId());
        if (data.getName() == null) {
            data.setName(donor.getName());
        }
        if (data.getEmail() == null) {
            data.setEmail(donor.getEmail());
        }
    }

    protected void validate(UserDto data) {
        if (storage.getAll().contains(UserMapper.toUser(data))) {
            throw new ValidationException("User  already exists");
        }
    }

}
