package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.resources.Common;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    private static final String FILE_PATH = "src/test/java/ru/practicum/shareit/resources/bookingJson/";

    BookingDtoResponse response;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingService bookingService;

    @Test
    public void addBooking() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        String json = Common.getFile(FILE_PATH + "addBooking.json");
        response = BookingDtoResponse.builder()
                .id(1)
                .start(localDateTime)
                .end(localDateTime.plusDays(1))
                .item(ItemDto.builder()
                        .id(1L)
                        .name("item")
                        .build())
                .status(StatusBooking.WAITING)
                .booker(UserDto.builder().id(1).name("User").build())
                .build();
        Mockito.when(bookingService.create(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                .thenReturn(response);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/bookings")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    public void approveBooking() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        response = BookingDtoResponse.builder()
                .id(1)
                .start(localDateTime)
                .end(localDateTime.plusDays(1))
                .item(ItemDto.builder()
                        .id(1L)
                        .name("item")
                        .build())
                .status(StatusBooking.WAITING)
                .booker(UserDto.builder().id(1).name("User").build())
                .build();
        Mockito.when(bookingService.approve(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
                .thenReturn(response);

        this.mockMvc
                .perform(MockMvcRequestBuilders.patch("/bookings/1")
                        .param("approved", "true")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getBookingById() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        response = BookingDtoResponse.builder()
                .id(1)
                .start(localDateTime)
                .end(localDateTime.plusDays(1))
                .item(ItemDto.builder()
                        .id(1L)
                        .name("item")
                        .build())
                .status(StatusBooking.WAITING)
                .booker(UserDto.builder().id(1).name("User").build())
                .build();
        Mockito.when(bookingService.getBookingById(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(response);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getUserBookings() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        Collection<BookingDtoResponse> responses = new ArrayList<>();
        response = BookingDtoResponse.builder()
                .id(1)
                .start(localDateTime)
                .end(localDateTime.plusDays(1))
                .item(ItemDto.builder()
                        .id(1L)
                        .name("item")
                        .build())
                .status(StatusBooking.WAITING)
                .booker(UserDto.builder().id(1).name("User").build())
                .build();
        responses.add(response);
        Mockito.when(bookingService.getBookingsByBooker(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                .thenReturn(responses);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/bookings")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getItemBookingsForOwner() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        Collection<BookingDtoResponse> responses = new ArrayList<>();
        response = BookingDtoResponse.builder()
                .id(1)
                .start(localDateTime)
                .end(localDateTime.plusDays(1))
                .item(ItemDto.builder()
                        .id(1L)
                        .name("item")
                        .build())
                .status(StatusBooking.WAITING)
                .booker(UserDto.builder().id(1).name("User").build())
                .build();
        responses.add(response);
        Mockito.when(bookingService.getItemBookingsByOwner(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                .thenReturn(responses);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/bookings/owner")
                        .contentType(MediaType.APPLICATION_JSON).header("X-Sharer-User-Id", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
