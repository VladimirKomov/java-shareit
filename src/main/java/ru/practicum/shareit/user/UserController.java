package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping
    public User addUser(@Valid @RequestBody UserDto userDto) {
        log.info("Create {}",  userDto.toString());
        return userService.create(UserMapper.toUser(userDto));
    }

    @PatchMapping("/{userId}")
    public User updateUser(@PathVariable long userId, @RequestBody UserDto userDto) {
        log.info("Update {}",  userDto.toString());
        return userService.update(userId, UserMapper.toUser(userDto));
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId) {
        log.info("GET Item id={}", userId);
        return userService.get(userId);
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        log.info("Items size {}", userService.getSize());
        return userService.getAll();
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("Delete by id={}", userId);
        userService.delete(userId);
    }

}
