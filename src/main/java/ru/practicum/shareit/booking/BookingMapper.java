package ru.practicum.shareit.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.booking.dto.BookingDtoRepository;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.util.Collection;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookingMapper {

    BookingMapper MAP_BOOKING = Mappers.getMapper(BookingMapper.class);

    Booking toBooking(BookingDtoRequest bookingDtoRequest);

    BookingDtoResponse toBookingDtoResponse(Booking booking);

    Collection<BookingDtoResponse> toCollectionBookingDtoResponse(Collection<Booking> bookings);

    @Mapping(target = "bookerId", source = "booker.id")
    @Mapping(target = "itemId", source = "item.id")
    BookingDtoRepository toBookingDtoRepository(Booking booking);

}
