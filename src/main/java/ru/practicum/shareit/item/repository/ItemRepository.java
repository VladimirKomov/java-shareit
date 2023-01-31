package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.Item;

import java.util.Collection;
import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(" select i from Item i " +
            "where upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%')) " +
            " and i.available is true ")
    List<Item> searchAvailableByNameAndDescription(String text);

    Collection<Item> findItemsByOwnerIdOrderById(long ownerId);

    //переделать в самом конце, можно без запроса
    @Query("select i from Item i " +
    " where i.request.id in ?1 ")
    Collection<Item> findItemsByRequestId(List<Long> listRequestId);
}
