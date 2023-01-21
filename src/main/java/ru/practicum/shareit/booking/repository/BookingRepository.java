package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.StatusBooking;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Collection<Booking> findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(long userId,
                                                                                       LocalDateTime dateTimeStart,
                                                                                       LocalDateTime localDateTimeEnd);

    Collection<Booking> findAllByBookerIdOrderByStartDesc(long userId);

    Collection<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime dateTime);

    Collection<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime dateTime);

    Collection<Booking> findAllByBookerIdAndStatusOrderByStartDesc(long userId, StatusBooking statusBooking);

    Collection<Booking> findAllByItemOwnerIdOrderByStartDesc(long userId);

    Collection<Booking> findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(long userId,
                                                                                          LocalDateTime dateTimeStart,
                                                                                          LocalDateTime localDateTimeEnd);

    Collection<Booking> findAllByItemOwnerIdAndEndIsBeforeOrderByStartDesc(long userId, LocalDateTime dateTime);

    Collection<Booking> findAllByItemOwnerIdAndStartIsAfterOrderByStartDesc(long userId, LocalDateTime dateTime);

    Collection<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(long userId, StatusBooking statusBooking);

    //Optional<Booking> findByIdAndAndBookerIdOrderByStartDesc(long bookingId, long bookerId);

    //Collection<Booking> findAllByBooker_IdAndItem_IdAndEndIsAfterOrderByStartDesc (long bookerId, long itemId, LocalDateTime dateTime);

//    @Query(" select b from Booking b " +
//    " where b.booker.id = ?1 and b.item.id = ?2 and b.end >= ?3 ")
//    Collection<Booking> findByBrookerIdItemIdTime (long bookerId, long itemId, LocalDateTime dateTime);

    @Query(" select b from Booking b " +
            " where b.item.id = ?1 and b.end >= ?2 ")
    Collection<Booking> findByItemIdAndTime (long itemId, LocalDateTime dateTime);

    @Query(" select b from Booking b " +
            " where b.booker.id = ?1 and b.end >= ?2 ")
    Collection<Booking> findByBookerIdAndTime (long bookerId, LocalDateTime dateTime);

    @Query(" select b from Booking b " +
            " where b.item.id = ?1 and b.start < ?2 " +
            " order by b.start desc " )
    Booking findLastItemBooking(long itemId, LocalDateTime now);

    @Query (" select b from Booking b " +
            " where b.item.id = ?1 and b.start > ?2 " +
            " order by b.start asc ")
    Booking findNextItemBooking(long itemId, LocalDateTime now);

    Booking findByItemIdAndItemOwnerIdAndEndIsBeforeOrderByStart(long itemId, long useId, LocalDateTime localDateTime);
    Booking findByItemIdAndItemOwnerIdAndStartIsAfterOrderByStart(long itemId, long useId, LocalDateTime localDateTime);


}
