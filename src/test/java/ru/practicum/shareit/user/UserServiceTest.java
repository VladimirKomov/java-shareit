package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static ru.practicum.shareit.user.UserMapper.MAP_USER;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    void addUser() {
        UserDto userDto = new UserDto(1, "name", "user@user.com");
        service.create(userDto);
        verify(repository, times(1)).save(MAP_USER.toUser(userDto));
    }

    @Test
    void updateUser() {
        UserDto userDto = new UserDto(1, "newName", "newUser@user.com");
        service.create(userDto);
        verify(repository, times(1)).save(MAP_USER.toUser(userDto));
    }

    @Test
    void deleteUser() {
        service.delete(1);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void getUserById() {
        UserDto userDto = new UserDto(1, "newName", "newUser@user.com");
        service.create(userDto);
        when(repository.findById(1L)).thenReturn(Optional.of(MAP_USER.toUser(userDto)));
        service.get(1);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getAllUsers() {
        User user = new User(1, "newName", "newUser@user.com");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(repository.findAll()).thenReturn(userList);
        service.getAll();
        verify(repository, times(1)).findAll();
    }
}
