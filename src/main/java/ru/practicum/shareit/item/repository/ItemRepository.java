package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.Item;

import java.util.Collection;
import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
//    long countById(Long id);
//
//    Collection<Item> findItemsByOwnerId(Long idSearch);
//
   @Query(" select i from Item i " +
            "where upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%')) " +
            " and i.available is true ")
    List<Item> searchAvailableByNameAndDescription(String text);

}
