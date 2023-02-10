package ru.practicum.shareit.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserClient userClient;

    /**
     * Создаёт объект пользователя
     */
    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDto userDto) {
        return userClient.create(userDto);
    }

    /**
     * Обновляет данные пользователя
     */
    @PatchMapping("{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable @Min(0) Long userId,
                                             @NonNull @RequestBody UserDto userDto) {
        return userClient.update(userId, userDto);
    }

    /**
     * Возвращает объект пользователя по ID
     */
    @GetMapping("{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable @Min(0) Long userId) {
        return userClient.getUser(userId);
    }

    /**
     * Возвращает список объектов всех пользователей
     */
    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return userClient.getUsers();
    }

    /**
     * Удаляет объект пользователя
     */
    @DeleteMapping("{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable @Min(0) long userId) {
        return userClient.deleteUser(userId);
    }
}
