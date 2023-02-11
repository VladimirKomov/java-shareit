package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class ItemRequestRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRequestRepository repository;

    @Test
    void findAllByRequestorIdOrderByCreatedDesc() {
        User requestor = User.builder().name("newName").email("newUser@user.com").build();
        ItemRequest itemRequest = ItemRequest.builder().description("descr").created(LocalDateTime.now()).requestor(requestor).build();
        entityManager.persist(requestor);
        entityManager.persist(itemRequest);
        Collection<ItemRequest> result = repository.findAllByRequestorIdOrderByCreatedDesc(requestor.getId());
        BDDAssertions.then(result).size().isEqualTo(1);
        BDDAssertions.then(result).containsExactlyElementsOf(List.of(itemRequest));
    }

}
