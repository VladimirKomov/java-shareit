package ru.practicum.shareit.booking;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

@Mapper(componentModel = "spring", uses = BookingMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookingMapper {

    BookingMapper MAP_BOOKING = Mappers.getMapper(BookingMapper.class);

    Booking toBooking(BookingDto bookingDto);

    BookingDtoResponse toBookingDtoResponse(Booking booking);

}
