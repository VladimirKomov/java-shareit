package ru.practicum.shareit.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Mapper(componentModel = "spring", uses = BookingMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookingMapper {

    BookingMapper MAP_BOOKING = Mappers.getMapper(BookingMapper.class);

    @Mapping(target = "start", source = "start", qualifiedByName = "toTimestamp")
    @Mapping(target = "end", source = "end", qualifiedByName = "toTimestamp")
    Booking toBooking(BookingDtoRequest bookingDtoRequest);

    @Named("toTimestamp")
    default Timestamp toTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    BookingDtoResponse toBookingDtoResponse(Booking booking);



}
