package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.AbstractService;
import ru.practicum.shareit.booking.model.Storage;
import ru.practicum.shareit.exception.NotFoundException;

import javax.validation.ValidationException;


@Service
public class UserService extends AbstractService<User> {

    @Autowired
    public UserService(Storage<User> storage) {
        this.storage = storage;
    }

    @Override
    protected void updateValues(long id, User data) {
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

    @Override
    protected void validate(User data) {
        if (storage.getAll().contains(data)) {
            throw new ValidationException("User  already exists");
        }
    }
}
