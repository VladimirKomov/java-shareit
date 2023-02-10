package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.exception.BadRequestException;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getBookings(long userId, String state, Integer from, Integer size) {
        BookingState bookingState = validateState(state);
        Map<String, Object> parameters = Map.of(
                "state", bookingState.name(),
                "from", from,
                "size", size
        );
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }

    private BookingState validateState(String state) {
        BookingState bookingState = BookingState.from(state)
                .orElseThrow(() -> new BadRequestException("Unknown state: " + state));
        return bookingState;
    }


    public ResponseEntity<Object> bookItem(long userId, BookItemRequestDto requestDto) {
        valideTime(requestDto);
        return post("", userId, requestDto);
    }

    private void valideTime(BookItemRequestDto requestDto) {
        if (requestDto.getStart().isAfter(requestDto.getEnd()) || requestDto.getEnd().equals(requestDto.getStart())) {
            throw new BadRequestException("Incorrect date");
        }
    }

    public ResponseEntity<Object> getBooking(long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> getByOwner(long userId, String state, int from, int size) {
        BookingState bookingState = validateState(state);
        Map<String, Object> parameters = Map.of(
                "state", bookingState.name(),
                "from", from,
                "size", size
        );
        return get("/owner?state={state}&from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> update(long userId, long bookingId, boolean approved) {
        Map<String, Object> parameters = Map.of("approved", approved);
        return patch("/" + bookingId + "?approved={approved}", userId, parameters, null);
    }
}
