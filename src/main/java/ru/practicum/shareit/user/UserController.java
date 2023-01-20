package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.Create;
import ru.practicum.shareit.common.Update;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.practicum.shareit.user.UserMapper.MAP_USER;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto addUser(@Validated(Create.class) @RequestBody UserDto userDto) {
        log.info("Create {}", userDto.toString());
        return MAP_USER.toUserDto(userService.create(userDto));
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable @Min(0) long userId,
                              @Validated(Update.class) @RequestBody UserDto userDto) {
        log.info("Update {}", userDto.toString());
        return MAP_USER.toUserDto(userService.update(userId, userDto));
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable @Min(0) long userId) {
        log.info("GET Item id={}", userId);
        return MAP_USER.toUserDto(userService.get(userId));
    }

    @GetMapping
    public Collection<UserDto> getAllUsers() {
        log.info("Items size {}", userService.getSize());
        return userService.getAll().stream()
                .map(MAP_USER::toUserDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @Min(0) long userId) {
        log.info("Delete by id={}", userId);
        userService.delete(userId);
    }
}
