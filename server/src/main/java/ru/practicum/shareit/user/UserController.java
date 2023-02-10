package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    /**
     * Создаёт объект пользователя
     */
    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        log.info("Create {}", userDto.toString());
        return userService.create(userDto);
    }

    /**
     * Обновляет данные пользователя
     */
    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable long userId,
                              @RequestBody UserDto userDto) {
        log.info("Update {}", userDto.toString());
        return userService.update(userId, userDto);
    }

    /**
     * Возвращает объект пользователя по ID
     */
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable long userId) {
        log.info("GET Item id={}", userId);
        return userService.get(userId);
    }

    /**
     * Возвращает список объектов всех пользователей
     */
    @GetMapping
    public Collection<UserDto> getAllUsers() {
        log.info("Items getAll");
        return userService.getAll();
    }

    /**
     * Удаляет объект пользователя
     */
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("Delete by id={}", userId);
        userService.delete(userId);
    }
}
