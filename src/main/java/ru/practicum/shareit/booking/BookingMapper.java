package ru.practicum.shareit.booking;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.item.dto.ItemDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookingMapper {

    BookingMapper MAP_BOOKING = Mappers.getMapper(BookingMapper.class);

//    @Mapping(target = "start", source = "start", qualifiedByName = "toTimestamp")
//    @Mapping(target = "end", source = "end", qualifiedByName = "toTimestamp")
    Booking toBooking(BookingDtoRequest bookingDtoRequest);

//    @Named("toTimestamp")
//    default Timestamp toTimestamp(LocalDateTime localDateTime) {
//        return Timestamp.valueOf(localDateTime);
//    }

//    @Named("toTimestamp")
//    default LocalDateTime toTimestamp(LocalDateTime localDateTime) {
//        return localDateTime;
//    }

    BookingDtoResponse toBookingDtoResponse(Booking booking);

   //    @Mapping(target = "id", ignore = true)
//    void update(ItemDto donor, @MappingTarget ItemDto target);


}
