package ru.practicum.shareit.request.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.Collection;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    Collection<ItemRequest> findAllByRequestorIdOrderByCreatedDesc(long userId);

    Collection<ItemRequest> findAllByRequestorIdOrderByCreatedDesc(long userId, PageRequest pageable);

}
