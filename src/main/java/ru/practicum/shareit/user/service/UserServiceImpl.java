package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserStorage userStorage;

    private long generateId = 0L;
    @Override
    public User addUser(User user) {
        user.setId(++generateId);
        return userStorage.addUser(user);
    }

    @Override
    public User updateUser(long userId, User user) {
        return userStorage.updateUser(userId, user);
    }

    @Override
    public void deleteUser(long userId) {
        userStorage.deleteUser(userId);
    }

    @Override
    public User getUserById(long userId) {
        return userStorage.getUserById(userId).orElseThrow(() -> new NotFoundException());
    }

    @Override
    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @Override
    public int getSize() {
        return userStorage.getSize();
    }
}
