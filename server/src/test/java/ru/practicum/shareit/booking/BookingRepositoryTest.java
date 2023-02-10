package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class BookingRepositoryTest {

    User owner;
    User booker;
    Item item;
    PageRequest pageRequest;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BookingRepository repository;

    @BeforeEach
    void setUp() {
        owner = User.builder().name("newName").email("newUser@user.com").build();
        booker = User.builder().name("requestor").email("requestor@user.com").build();
        item = Item.builder()
                .name("Дрель")
                .description("description")
                .available(true)
                .owner(owner)
                .build();
        entityManager.persist(owner);
        entityManager.persist(booker);
        entityManager.persist(item);
        pageRequest = PageRequest.of(0, 1);
    }

    @Test
    void findAllByBookerIdOrderByStartDesc() {
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(LocalDateTime.now().plusMinutes(10))
                .end(LocalDateTime.now().plusDays(1))
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Collection<Booking> result = repository.findAllByBookerIdOrderByStartDesc(booker.getId(), pageRequest);
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(booking));
    }

    @Test
    void findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc() {

        LocalDateTime before = LocalDateTime.now().minusDays(1);
        LocalDateTime after = LocalDateTime.now().plusDays(1);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Collection<Booking> result = repository.findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
                booker.getId(), LocalDateTime.now(), LocalDateTime.now(), pageRequest);
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(booking));
    }

    @Test
    void findAllByBookerIdAndEndBeforeOrderByStartDesc() {
        LocalDateTime before = LocalDateTime.now().minusDays(10);
        LocalDateTime after = LocalDateTime.now().minusDays(8);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Collection<Booking> result = repository.findAllByBookerIdAndEndBeforeOrderByStartDesc(
                booker.getId(), LocalDateTime.now(), pageRequest);
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(booking));
    }

    @Test
    void findAllByBookerIdAndStartAfterOrderByStartDesc() {
        LocalDateTime before = LocalDateTime.now().plusDays(10);
        LocalDateTime after = LocalDateTime.now().plusDays(11);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Collection<Booking> result = repository.findAllByBookerIdAndStartAfterOrderByStartDesc(
                booker.getId(), LocalDateTime.now(), pageRequest);
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(booking));
    }

    @Test
    void findAllByBookerIdAndStatusOrderByStartDesc() {
        LocalDateTime before = LocalDateTime.now().plusDays(10);
        LocalDateTime after = LocalDateTime.now().plusDays(11);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Collection<Booking> result = repository.findAllByBookerIdAndStatusOrderByStartDesc(
                booker.getId(), StatusBooking.WAITING, pageRequest);
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(booking));
    }

    @Test
    void findAllByItemOwnerIdOrderByStartDesc() {
        LocalDateTime before = LocalDateTime.now().plusDays(10);
        LocalDateTime after = LocalDateTime.now().plusDays(11);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Collection<Booking> result = repository.findAllByItemOwnerIdOrderByStartDesc(owner.getId(), pageRequest);
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(booking));
    }

    @Test
    void findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc() {
        LocalDateTime before = LocalDateTime.now().minusDays(1);
        LocalDateTime after = LocalDateTime.now().plusDays(11);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Collection<Booking> result = repository.findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
                owner.getId(), LocalDateTime.now(), LocalDateTime.now(), pageRequest);
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(booking));
    }

    @Test
    void findByItemIdInAndEndBeforeOrderByStartDesc() {
        LocalDateTime before = LocalDateTime.now().minusDays(12);
        LocalDateTime after = LocalDateTime.now().minusDays(11);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Collection<Booking> result = repository.findAllByItemOwnerIdAndEndIsBeforeOrderByStartDesc(
                owner.getId(), LocalDateTime.now(), pageRequest);
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(booking));
    }

    @Test
    void findAllByItemOwnerIdAndStartIsAfterOrderByStartDesc() {
        LocalDateTime before = LocalDateTime.now().plusDays(11);
        LocalDateTime after = LocalDateTime.now().plusDays(12);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Collection<Booking> result = repository.findAllByItemOwnerIdAndStartIsAfterOrderByStartDesc(
                owner.getId(), LocalDateTime.now(), pageRequest);
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(booking));
    }

    @Test
    void findAllByItemOwnerIdAndStatusOrderByStartDesc() {
        LocalDateTime before = LocalDateTime.now().plusDays(11);
        LocalDateTime after = LocalDateTime.now().plusDays(12);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Collection<Booking> result = repository.findAllByItemOwnerIdAndStatusOrderByStartDesc(
                owner.getId(), StatusBooking.WAITING, pageRequest);
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(booking));
    }

    @Test
    void findByItemIdAndItemOwnerIdAndEndIsBeforeOrderByStart() {
        LocalDateTime before = LocalDateTime.now().minusDays(12);
        LocalDateTime after = LocalDateTime.now().minusDays(11);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Booking result = repository.findByItemIdAndItemOwnerIdAndEndIsBeforeOrderByStart(
                item.getId(), owner.getId(), LocalDateTime.now());
        then(booking).isEqualTo(result);

    }

    @Test
    void findByItemIdAndItemOwnerIdAndStartIsAfterOrderByStart() {
        LocalDateTime before = LocalDateTime.now().plusDays(11);
        LocalDateTime after = LocalDateTime.now().plusDays(12);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Booking result = repository.findByItemIdAndItemOwnerIdAndStartIsAfterOrderByStart(item.getId(),
                owner.getId(), LocalDateTime.now());
        then(booking).isEqualTo(result);
    }

    @Test
    void findByBookerIdAndItemIdAndEndBefore() {
        LocalDateTime before = LocalDateTime.now().minusDays(12);
        LocalDateTime after = LocalDateTime.now().minusDays(11);
        Booking booking = Booking.builder()
                .booker(booker)
                .item(item)
                .start(before)
                .end(after)
                .status(StatusBooking.WAITING).build();
        entityManager.persist(booking);
        Collection<Booking> result = repository.findAllByBookerIdAndItemIdAndStatusAndStartBeforeOrderByStartDesc(
                booker.getId(), item.getId(), StatusBooking.WAITING, LocalDateTime.now());
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(booking));
    }
}
