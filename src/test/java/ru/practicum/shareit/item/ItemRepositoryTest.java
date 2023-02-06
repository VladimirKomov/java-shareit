package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class ItemRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRepository repository;

    @Test
    void findItemsByOwnerId() {
        User user = User.builder().name("newName").email("newUser@user.com").build();
        Item item = Item.builder()
                .name("Дрель")
                .description("description")
                .available(true).owner(user)
                .build();
        entityManager.persist(user);
        entityManager.persist(item);
        Collection<Item> result = repository.findItemsByOwnerIdOrderById(user.getId(), PageRequest.of(0, 1));
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(item));
    }

    @Test
    void findAllByRequestId() {
        User owner = User.builder().name("newName").email("newUser@user.com").build();
        User requestor = User.builder().name("requestor").email("requestor@user.com").build();
        ItemRequest itemRequest = ItemRequest.builder().description("descr").created(LocalDateTime.now()).requestor(requestor).build();
        Item item = Item.builder()
                .name("Дрель")
                .description("дрель аккумуляторная")
                .available(true)
                .owner(owner)
                .request(itemRequest)
                .build();
        entityManager.persist(owner);
        entityManager.persist(requestor);
        entityManager.persist(itemRequest);
        entityManager.persist(item);
        Collection<Item> result = repository.findAllByRequestIdIn(List.of(itemRequest.getId()));
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(item));
    }

    @Test
    void searchByNameAndDescription() {
        User owner = User.builder().name("newName").email("newUser@user.com").build();
        Item item = Item.builder()
                .name("Дрель")
                .description("дрель аккумуляторная")
                .available(true)
                .owner(owner)
                .build();
        entityManager.persist(owner);
        entityManager.persist(item);
        Collection<Item> result = repository.searchAvailableByNameAndDescription("АккУМ", PageRequest.of(0, 1));
        then(result).size().isEqualTo(1);
        then(result).containsExactlyElementsOf(List.of(item));
    }
}
