package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.resources.Common;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String FILE_PATH = "src/test/java/ru/practicum/shareit/resources/userJson/";
    UserDto userDto;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void addUser() throws Exception {
        String json = Common.getFile(FILE_PATH + "addUser.json");
        userDto = new UserDto(1, "name", "user@user.com");
        Mockito.when(userService.create(ArgumentMatchers.any()))
                .thenReturn(userDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("user@user.com")));
    }


    @Test
    public void updateUser() throws Exception {
        String json = Common.getFile(FILE_PATH + "updateUser.json");
        this.mockMvc
                .perform(MockMvcRequestBuilders.patch("/users/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getUserById() throws Exception {
        userDto = new UserDto(1, "update", "update@mail.com");

        Mockito.when(userService.get(1))
                .thenReturn(userDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("update@mail.com")));
    }

    @Test
    public void getAllUsers() throws Exception {
        userDto = new UserDto(1, "update", "update@mail.com");

        Collection<UserDto> userDtoCollection = new ArrayList<>();
        userDtoCollection.add(userDto);
        Mockito.when(userService.getAll())
                .thenReturn(userDtoCollection);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteItemById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
