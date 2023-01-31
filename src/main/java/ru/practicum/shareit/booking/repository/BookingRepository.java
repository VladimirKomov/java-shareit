package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.StatusBooking;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Collection<Booking> findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(long userId,
                                                                                       LocalDateTime dateTimeStart,
                                                                                       LocalDateTime localDateTimeEnd,
                                                                                       PageRequest pageRequest);

    Collection<Booking> findAllByBookerIdOrderByStartDesc(long userId, PageRequest pageRequest);

    Collection<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime dateTime
            , PageRequest pageRequest);

    Collection<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime dateTime,
                                                                      PageRequest pageRequest);

    Collection<Booking> findAllByBookerIdAndStatusOrderByStartDesc(long userId, StatusBooking statusBooking,
                                                                   PageRequest pageRequest);

    Collection<Booking> findAllByItemOwnerIdOrderByStartDesc(long userId, PageRequest pageRequest);

    Collection<Booking> findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(long userId,
                                                                                          LocalDateTime dateTimeStart,
                                                                                          LocalDateTime localDateTimeEnd,
                                                                                          PageRequest pageRequest);

    Collection<Booking> findAllByItemOwnerIdAndEndIsBeforeOrderByStartDesc(long userId, LocalDateTime dateTime,
                                                                           PageRequest pageRequest);

    Collection<Booking> findAllByItemOwnerIdAndStartIsAfterOrderByStartDesc(long userId, LocalDateTime dateTime,
                                                                            PageRequest pageRequest);

    Collection<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(long userId, StatusBooking statusBooking,
                                                                      PageRequest pageRequest);

    Booking findByItemIdAndItemOwnerIdAndEndIsBeforeOrderByStart(long itemId, long useId, LocalDateTime localDateTime);

    Booking findByItemIdAndItemOwnerIdAndStartIsAfterOrderByStart(long itemId, long useId, LocalDateTime localDateTime);

    Collection<Booking> findAllByBookerIdAndItemIdAndStatusAndStartBeforeOrderByStartDesc(long userId, long itemId,
                                                                                          StatusBooking statusBooking,
                                                                                          LocalDateTime now);

}
