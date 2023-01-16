package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.Item;

import java.util.Collection;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    long countById(Long id);

    Collection<Item> findItemsByOwnerId(Long idSearch);

    @Query(
            "SELECT i FROM Item i " +
                    "WHERE UPPER(i.name) LIKE UPPER(CONCAT('%', ?1, '%')) " +
                    "OR UPPER(i.description) LIKE UPPER(CONCAT('%', ?1, '%'))"
    )
    List<Item> searchByNameAndDescription(String text);

}
